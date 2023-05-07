package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Platform {

    @Embedded
    private PlatformUrl platformUrl;

    @Embedded
    private PlatformType platformType;

    @Embedded
    private PlatformActivityName platformActivityName;

    private Platform(PlatformUrl platformUrl, PlatformType platformType, PlatformActivityName platformActivityName) {
        this.platformUrl = platformUrl;
        this.platformType = platformType;
        this.platformActivityName = platformActivityName;
    }

    public static Platform of(String platformUrl, String platformType, String platformActivityName) {
        return new Platform(
                PlatformUrl.from(platformUrl),
                PlatformType.from(platformType),
                PlatformActivityName.from(platformActivityName)
        );
    }
}
