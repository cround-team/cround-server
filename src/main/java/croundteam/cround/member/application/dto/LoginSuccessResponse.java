package croundteam.cround.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginSuccessResponse {

    private String accessToken;
    private String refreshToken;
    private String roleName;

    public LoginSuccessResponse(TokenResponse tokenResponse, String roleName) {
        this.accessToken = tokenResponse.getAccessToken();
        this.refreshToken = tokenResponse.getRefreshToken();
        this.roleName = roleName;
    }

    public String extractByAccessToken() {
        return accessToken.substring(7);
    }

    public String extractByRefreshToken() {
        return refreshToken.substring(7);
    }
}
