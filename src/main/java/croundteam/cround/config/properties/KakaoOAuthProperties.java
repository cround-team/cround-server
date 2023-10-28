package croundteam.cround.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter @Getter
@Configuration
@ConfigurationProperties(prefix = "oauth2.client.provider.kakao")
public class KakaoOAuthProperties {

    private String grantType;
    private String clientId;
    private String redirectUri;
    private String clientSecret;
}
