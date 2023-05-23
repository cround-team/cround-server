package croundteam.cround.member.domain.interest;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {

    /**
     * 플랫폼 여러개
     * 테마 여러개
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "interest_platform",
            joinColumns = @JoinColumn(
                    name = "member_id"))
    private List<PlatformType> interestPlatform = new ArrayList<>();

    public Interest(List<PlatformType> interestPlatform) {
        this.interestPlatform = interestPlatform;
    }

    public static Interest from(List<PlatformType> platformTypes) {
        return new Interest(platformTypes);
    }
}
