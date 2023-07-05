package croundteam.cround.creator.application.dto;

import croundteam.cround.creator.domain.Creator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatorUpdateResponse {

    private String profileImage;
    private String nickname;

    public CreatorUpdateResponse(String profileImage, String nickname) {
        this.profileImage = profileImage;
        this.nickname = nickname;
    }

    public static CreatorUpdateResponse create(Creator creator) {
        return new CreatorUpdateResponse(creator.getProfileImage(), creator.getNickname());
    }
}
