package croundteam.cround.member.application.client;

import feign.form.FormProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
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
                .code("Bearer " + code)
                .grantType(googleOAuthProperties.getGrantType())
                .clientId(googleOAuthProperties.getClientId())
                .redirectUri(googleOAuthProperties.getRedirectUri())
                .build();
    }
}
