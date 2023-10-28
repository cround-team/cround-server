package croundteam.cround.member.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.config.properties.KakaoOAuthProperties;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.CreatorRepository;
import croundteam.cround.member.application.client.KakaoAuthClient;
import croundteam.cround.member.application.client.KakaoInfoClient;
import croundteam.cround.member.application.dto.KakaoOAuthRequest;
import croundteam.cround.member.application.dto.LoginSuccessResponse;
import croundteam.cround.member.application.dto.MemberLoginRequest;
import croundteam.cround.member.application.dto.TokenResponse;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.exception.PasswordMisMatchException;
import croundteam.cround.security.token.OAuthTokenResponse;
import croundteam.cround.security.token.RefreshToken;
import croundteam.cround.security.token.RefreshTokenRepository;
import croundteam.cround.support.BCryptEncoder;
import croundteam.cround.support.TokenProvider;
import croundteam.cround.support.vo.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoAuthClient kakaoOAuthClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final KakaoOAuthProperties kakaoOAuthProperties;

    @Transactional
    public LoginSuccessResponse login(final MemberLoginRequest memberLoginRequest) {
        Member member = findMemberByEmail(memberLoginRequest);

        validateIsSamePassword(member, memberLoginRequest);

        TokenResponse tokenResponse = issueToken(member);
        refreshTokenRepository.save(createRefreshToken(tokenResponse, member.getId()));

        Creator creator = findCreatorByMember(member);

        return new LoginSuccessResponse(tokenResponse, member, creator);
    }

    private Creator findCreatorByMember(Member member) {
        return creatorRepository.findCreatorByMember(member).orElse(null);
    }

    @Transactional
    public LoginSuccessResponse loginByOAuthV2(String provider, String code) {
        KakaoOAuthRequest request = KakaoOAuthRequest.from(code, kakaoOAuthProperties);
        OAuthTokenResponse response = kakaoOAuthClient.getToken(request.toString());
        Map<String, Object> userInfo = kakaoInfoClient.getUserInfo("Bearer " + response.getAccessToken());
        OAuthAttributes attributes = OAuthAttributes.of(provider, "email", userInfo);
        log.info("=> {} 님이 인증에 성공하였습니다.", attributes.getName());

        Member member = saveOrUpdate(attributes);
        log.info("=> social {} 에서 {} 이 로그인 하였습니다.", provider, member.getUsername());

        TokenResponse tokenResponse = issueToken(member);
        refreshTokenRepository.save(createRefreshToken(tokenResponse, member.getId()));

        Creator creator = findCreatorByMember(member);

        return new LoginSuccessResponse(tokenResponse, member, creator);
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail()).orElseGet(attributes::toEntity);
        member.update(member);
        return memberRepository.save(member);
    }

    private RefreshToken createRefreshToken(TokenResponse tokenResponse, Long memberId) {
        return new RefreshToken(tokenResponse.extractByRefreshToken(), memberId);
    }

    private TokenResponse issueToken(Member member) {
        return tokenProvider.generateToken(member.getEmail());
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
