package croundteam.cround.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String roleName;

    public LoginResponse(String accessToken, String roleName) {
        this.accessToken = accessToken;
        this.roleName = roleName;
    }

    public static LoginResponse create(LoginSuccessResponse loginSuccessResponse) {
        return new LoginResponse(loginSuccessResponse.extractByAccessToken(), loginSuccessResponse.getRoleName());
    }
}
