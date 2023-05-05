package croundteam.cround.auth.application.oauth;

import croundteam.cround.auth.support.OAuthAttributes;
import croundteam.cround.member.domain.Role;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        /**
         * 회원가입을 한 사용자인지 확인한 다음 회원가입하지 않은 사용자라면 db의 정보를 저장
         */
        String registrationId = oAuth2UserRequest
                .getClientRegistration()
                .getRegistrationId()
                .toUpperCase();
        String userNameAttributeName = oAuth2UserRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes oAuth2Attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Map<String, Object> memberAttribute = oAuth2Attributes.convertToMap();
        log.info(">> OAuth2 Attributes (Map) = {}", oAuth2Attributes.getAttributes());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey())),
                memberAttribute,
                "email"
        );
    }
}
