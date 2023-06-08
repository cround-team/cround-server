package croundteam.cround.member.application.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String excludeBearerInRefreshToken() {
        return refreshToken.substring(7);
    }
}
