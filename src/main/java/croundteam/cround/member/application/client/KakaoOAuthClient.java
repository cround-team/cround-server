package croundteam.cround.member.application.client;

import croundteam.cround.security.token.OAuthTokenResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakaoOAuthClient", url = "${oauth2.client.provider.kakao.root-url}", configuration = KakaoOAuthClientConfig.class)
public interface KakaoOAuthClient {

    @PostMapping("/oauth/token")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    OAuthTokenResponse getToken(@RequestBody KakaoOAuthRequest request);

}
