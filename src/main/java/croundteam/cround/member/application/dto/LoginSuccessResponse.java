package croundteam.cround.member.application.dto;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class LoginSuccessResponse {

    private String accessToken;
    private String refreshToken;
    private String roleName;
    private String nickname;
    private boolean isSocialLogin;
    private String profileImage;
    private Long creatorId;
    private Long memberId;

    public LoginSuccessResponse(TokenResponse tokenResponse, Member member, Creator creator) {
        this.accessToken = tokenResponse.getAccessToken();
        this.refreshToken = tokenResponse.getRefreshToken();
        this.roleName = member.getRoleName();
        this.nickname = getCurrentUserNickname(member, creator);
        this.isSocialLogin = member.isSocial();
        this.memberId = member.getId();
        if(Objects.nonNull(creator)) {
            this.profileImage = creator.getProfileImage();
            this.creatorId = creator.getId();
        }
    }

    private String getCurrentUserNickname(Member member, Creator creator) {
        if(Objects.nonNull(creator)) {
            return creator.getNickname();
        }
        return member.getNickname();
    }

    public String extractByAccessToken() {
        return accessToken.substring(7);
    }

    public String extractByRefreshToken() {
        return refreshToken.substring(7);
    }
}
