package croundteam.cround.creator.domain;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityPlatforms {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "creator_activity_platform",
            joinColumns = @JoinColumn(
                    name = "creator_id"),
            foreignKey = @ForeignKey(name = "fk_activity_platform_to_creator"))
    @Enumerated(EnumType.STRING)
    private List<PlatformType> platformTypes = new ArrayList<>();

    public ActivityPlatforms(List<PlatformType> platformTypes) {
        this.platformTypes = platformTypes;
    }

    public static ActivityPlatforms castToActivityPlatforms(List<String> activityPlatforms) {
        List<PlatformType> temp = activityPlatforms.stream().map(PlatformType::create).collect(Collectors.toList());
        return new ActivityPlatforms(temp);
    }

    public List<PlatformType> getPlatformTypes() {
        return platformTypes;
    }
}
