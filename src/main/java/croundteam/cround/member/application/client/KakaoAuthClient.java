package croundteam.cround.member.application.client;

import croundteam.cround.security.token.OAuthTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakaoClient", url = "${oauth2.client.provider.kakao.auth-root-url}", configuration = KakaoClientConfig.class)
public interface KakaoAuthClient {

    @PostMapping("/oauth/token")
    OAuthTokenResponse getToken(@RequestBody String request);
}
