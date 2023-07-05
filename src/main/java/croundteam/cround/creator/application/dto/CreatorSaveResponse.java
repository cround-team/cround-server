package croundteam.cround.creator.application.dto;

import croundteam.cround.creator.domain.Creator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatorSaveResponse {

    private String profileImage;
    private Long creatorId;

    public CreatorSaveResponse(String profileImage, Long creatorId) {
        this.profileImage = profileImage;
        this.creatorId = creatorId;
    }

    public static CreatorSaveResponse create(Creator saveCreator) {
        return new CreatorSaveResponse(saveCreator.getProfileImage(), saveCreator.getId());
    }
}
