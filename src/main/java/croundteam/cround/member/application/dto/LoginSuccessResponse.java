package croundteam.cround.member.application.dto;

import croundteam.cround.creator.domain.Creator;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class LoginSuccessResponse {

    private String accessToken;
    private String refreshToken;
    private String roleName;
    private String profileImage;
    private Long creatorId;

    public LoginSuccessResponse(TokenResponse tokenResponse, String roleName, Creator creator) {
        this.accessToken = tokenResponse.getAccessToken();
        this.refreshToken = tokenResponse.getRefreshToken();
        this.roleName = roleName;
        if(Objects.nonNull(creator)) {
            this.profileImage = creator.getProfileImage();
            this.creatorId = creator.getId();
        }
    }

    public String extractByAccessToken() {
        return accessToken.substring(7);
    }

    public String extractByRefreshToken() {
        return refreshToken.substring(7);
    }
}
