package croundteam.cround.member.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import croundteam.cround.config.properties.KakaoOAuthProperties;
import feign.form.FormProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoOAuthRequest {

    private String code;

    @FormProperty("grant_type")
    private String grantType;

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("redirect_uri")
    private String redirectUri;

    @Builder
    public KakaoOAuthRequest(final String code, final String grantType, final String clientId, final String redirectUri) {
        this.code = code;
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public static KakaoOAuthRequest from(final String code, final KakaoOAuthProperties googleOAuthProperties) {
        return KakaoOAuthRequest.builder()
                .code(code)
                .grantType(googleOAuthProperties.getGrantType())
                .clientId(googleOAuthProperties.getClientId())
                .redirectUri(googleOAuthProperties.getRedirectUri())
                .build();
    }

    @Override
    public String toString() {
        return "code=" + code + '&' +
                "client_id=" + clientId + '&' +
                "redirect_uri=" + redirectUri + '&' +
                "grant_type=" + grantType;
    }
}
