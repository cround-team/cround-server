package croundteam.cround.member.domain.interest;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestPlatforms {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_platform",
            joinColumns = @JoinColumn(
                    name = "member_id"))
    private List<PlatformType> platformTypes = new ArrayList<>();

    public InterestPlatforms(List<PlatformType> platformTypes) {
        this.platformTypes = platformTypes;
    }

    public static InterestPlatforms create(List<PlatformType> platformTypes) {
        return new InterestPlatforms(platformTypes);
    }
}
