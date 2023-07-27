package croundteam.cround.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String roleName;
    private String nickname;
    private String profileImage;
    private Long creatorId;
    private Long memberId;
    private boolean isSocialLogin;

    public LoginResponse(String accessToken, String roleName, String nickname, String profileImage,
                         Long creatorId, Long memberId, boolean isSocialLogin) {
        this.accessToken = accessToken;
        this.roleName = roleName;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.creatorId = creatorId;
        this.memberId = memberId;
        this.isSocialLogin = isSocialLogin;
    }

    public static LoginResponse create(LoginSuccessResponse loginSuccessResponse) {
        return new LoginResponse(
                loginSuccessResponse.extractByAccessToken(), loginSuccessResponse.getRoleName(),
                loginSuccessResponse.getNickname(), loginSuccessResponse.getProfileImage(),
                loginSuccessResponse.getCreatorId(), loginSuccessResponse.getMemberId(),
                loginSuccessResponse.isSocialLogin());
    }
}
