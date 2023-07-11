package croundteam.cround.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String roleName;
    private String profileImage;
    private Long creatorId;

    public LoginResponse(String accessToken, String roleName, String profileImage, Long creatorId) {
        this.accessToken = accessToken;
        this.roleName = roleName;
        this.profileImage = profileImage;
        this.creatorId = creatorId;
    }

    public static LoginResponse create(LoginSuccessResponse loginSuccessResponse) {
        return new LoginResponse(
                loginSuccessResponse.extractByAccessToken(), loginSuccessResponse.getRoleName(),
                loginSuccessResponse.getProfileImage(), loginSuccessResponse.getCreatorId());
    }
}
