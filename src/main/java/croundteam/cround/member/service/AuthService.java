package croundteam.cround.member.service;

import croundteam.cround.security.oauth2.OAuthAttributes;
import croundteam.cround.security.token.OAuthTokenResponse;
import croundteam.cround.security.token.RefreshToken;
import croundteam.cround.security.token.RefreshTokenRepository;
import croundteam.cround.security.BCryptEncoder;
import croundteam.cround.security.token.support.TokenProvider;
import croundteam.cround.common.dto.TokenResponse;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.NotExistMemberException;
import croundteam.cround.common.exception.member.PasswordMisMatchException;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.dto.MemberLoginRequest;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final InMemoryClientRegistrationRepository inMemoryRepository;

    @Transactional
    public TokenResponse login(final MemberLoginRequest memberLoginRequest) {
        Member member = findMemberByEmail(memberLoginRequest);

        validateIsSamePassword(member, memberLoginRequest);

        TokenResponse tokenResponse = issueToken(member);
        refreshTokenRepository.save(createRefreshToken(tokenResponse, member.getId()));

        return tokenResponse;
    }

    @Transactional
    public TokenResponse loginByOAuth(String provider, String code) {
        ClientRegistration clientRegistration = inMemoryRepository.findByRegistrationId(provider);
        OAuthTokenResponse oAuthTokenResponse = getToken(clientRegistration, code);
        Map<String, Object> userAttributes = getUserAttributes(clientRegistration, oAuthTokenResponse);
        OAuthAttributes attributes = OAuthAttributes.of(provider, "email", userAttributes);
        log.info("=> {} 님이 인증에 성공하였습니다.", attributes.getName());

        Member member = saveOrUpdate(attributes);
        log.info("=> social {} 에서 {} 이 로그인 하였습니다.", provider, member.getUsername());

        TokenResponse tokenResponse = issueToken(member);
        refreshTokenRepository.save(createRefreshToken(tokenResponse, member.getId()));

        return tokenResponse;
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail()).orElseGet(() -> attributes.toEntity());
        member.update(member);
        return memberRepository.save(member);
    }

    private RefreshToken createRefreshToken(TokenResponse tokenResponse, Long memberId) {
        return new RefreshToken(tokenResponse.excludeBearerInRefreshToken(), memberId);
    }

    private TokenResponse issueToken(Member member) {
        return tokenProvider.generateToken(member.getEmail());
    }

    private OAuthTokenResponse getToken(ClientRegistration provider, String code) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    private Object tokenRequest(String code, ClientRegistration provider) {
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("client_id", provider.getClientId());
        return formData;
    }

    private Map<String, Object> getUserAttributes(ClientRegistration provider, OAuthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private void validateIsSamePassword(Member member, MemberLoginRequest memberLoginRequest) {
        if(!BCryptEncoder.isSamePassword(member, memberLoginRequest.getPassword())) {
            throw new PasswordMisMatchException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    private Member findMemberByEmail(MemberLoginRequest memberLoginRequest) {
        return memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }


}
