package croundteam.cround.creator.application.dto;

import croundteam.cround.creator.domain.Creator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchCreatorResponse {

    private Long creatorId;
    private String creatorNickname;
    private String description;
    private String profileImage;
    private String platformType;
    private String platformTheme;

    @Builder
    public SearchCreatorResponse(Long creatorId, String creatorNickname, String description, String profileImage,
                                 String platformType, String platformTheme) {
        this.creatorId = creatorId;
        this.creatorNickname = creatorNickname;
        this.description = description;
        this.profileImage = profileImage;
        this.platformType = platformType;
        this.platformTheme = platformTheme;
    }

    public static SearchCreatorResponse from(Creator creator) {
        return SearchCreatorResponse.builder()
                .creatorId(creator.getId())
                .creatorNickname(creator.getNickname())
                .description(creator.getDescription())
                .profileImage(creator.getProfileImage())
                .platformType(creator.getPlatformType())
                .platformTheme(creator.getPlatformTheme())
                .build();
    }
}
