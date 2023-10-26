package croundteam.cround.member.application.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "kakaoClient", url = "${oauth2.client.provider.kakao.user-info-root-uri}", configuration = KakaoClientConfig.class)
public interface KakaoClient {

    @GetMapping("/v2/user/me")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Map<String, Object> getUserInfo(@RequestHeader("Authorization") String authorization);

}
