package croundteam.cround.creator.application.dto;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.Description;
import croundteam.cround.creator.domain.ProfileImage;
import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.member.domain.Nickname;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static croundteam.cround.creator.domain.ActivityPlatforms.castToActivityPlatforms;
import static croundteam.cround.tag.domain.Tags.castToTags;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatorSaveRequest {

    private String nickname;
    private String description;
    private String platformHeadType;
    private String platformHeadTheme;
    private String platformUrl;
    private List<String> tags = new ArrayList<>();
    private List<String> activityPlatforms = new ArrayList<>();

    public Creator toEntity() {
        return Creator.builder()
                .nickname(Nickname.create(nickname))
                .description(Description.create(description))
                .platform(Platform.of(platformHeadTheme, platformHeadType, platformUrl))
                .tags(castToTags(tags))
                .activityPlatforms(castToActivityPlatforms(activityPlatforms))
                .build();
    }
}
