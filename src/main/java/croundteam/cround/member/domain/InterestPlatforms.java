package croundteam.cround.member.domain;

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
            name = "member_interest_platform",
            joinColumns = @JoinColumn(
                    name = "member_id"))
    @Enumerated(EnumType.STRING)
    private List<PlatformType> interestPlatforms = new ArrayList<>();

    private InterestPlatforms(List<PlatformType> interestPlatforms) {
        this.interestPlatforms = interestPlatforms;
    }

    public static InterestPlatforms create(List<PlatformType> platformTypes) {
        return new InterestPlatforms(platformTypes);
    }
}
