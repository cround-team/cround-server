package croundteam.cround.creator.service.dto;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.Description;
import croundteam.cround.creator.domain.ProfileImage;
import croundteam.cround.creator.domain.platform.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static croundteam.cround.creator.domain.ActivityPlatforms.castToActivityPlatforms;
import static croundteam.cround.creator.domain.tag.Tags.castToTags;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatorSaveRequest {

    private String profileImage;
    private String platformHeadTheme;
    private List<String> tags = new ArrayList<>();
    private String platformHeadType;
    private List<String> activityPlatforms = new ArrayList<>();
    private String platformUrl;
    private String platformActivityName;
    private String description;

    public Creator toEntity() {
        return Creator.builder()
                .profileImage(ProfileImage.create(profileImage))
                .platform(Platform.of(platformHeadTheme, platformHeadType, platformUrl, platformActivityName))
                .tags(castToTags(tags))
                .activityPlatforms(castToActivityPlatforms(activityPlatforms))
                .description(Description.create(description))
                .build();
    }
}
