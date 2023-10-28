package croundteam.cround.member.application.client;

import croundteam.cround.config.feign.KakaoClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "kakaoInfoClient", url = "${oauth2.client.provider.kakao.info-root-url}", configuration = KakaoClientConfig.class)
public interface KakaoInfoClient {

    @GetMapping("/v2/user/me")
    Map<String, Object> getUserInfo(@RequestHeader("Authorization") String authorization);

}
