package croundteam.cround.creator.service.dto;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.Platform;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatorSearchResponse {

    private Long creatorId;
    private String platformActivityName;
    private String description;
    private String profileImage;
    private String platformType;

    @Builder
    public CreatorSearchResponse(Long creatorId, String platformActivityName, String description, String profileImage, String platformType) {
        this.creatorId = creatorId;
        this.platformActivityName = platformActivityName;
        this.description = description;
        this.profileImage = profileImage;
        this.platformType = platformType;
    }

    public static CreatorSearchResponse from(Creator creator) {
        return CreatorSearchResponse.builder()
                .creatorId(creator.getId())
                .platformActivityName(creator.getActivityName())
                .description(creator.getDescription())
                .profileImage(creator.getProfileImage())
                .platformType(creator.getPlatform().getPlatformType())
                .build();
    }
}
