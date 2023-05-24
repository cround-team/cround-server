package croundteam.cround.creator.service.dto;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.Description;
import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.creator.domain.tag.Tag;
import croundteam.cround.creator.domain.tag.Tags;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatorSaveRequest {

    private String profileImage;
    private String platformUrl;
    private String platformType;
    private String platformActivityName;
    private String description;
    private List<Tag> tags = new ArrayList<>();

    public Creator toEntity() {
        return Creator.builder()
                .profileImage(profileImage)
                .platform(Platform.of(platformUrl, platformType, platformActivityName))
                .creatorTags(Tags.from(tags))
                .description(Description.create(description))
                .build();
    }
}
