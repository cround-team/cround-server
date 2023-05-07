package croundteam.cround.member.domain.interest;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {

    @Embedded
    private PlatformType platformType;

    @Embedded
    private Category category;

    public Interest(PlatformType platformType, Category category) {
        this.platformType = platformType;
        this.category = category;
    }
}
