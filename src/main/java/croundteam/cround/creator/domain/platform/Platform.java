package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Platform {

    @Embedded
    private PlatformTheme platformTheme;

    @Embedded
    private PlatformUrl platformUrl;

    @Embedded
    private PlatformActivityName platformActivityName;

    @Embedded
    private PlatformType platformType;

    private Platform(PlatformTheme platformTheme, PlatformUrl platformUrl,
                     PlatformActivityName platformActivityName, PlatformType platformType) {
        this.platformTheme = platformTheme;
        this.platformUrl = platformUrl;
        this.platformActivityName = platformActivityName;
        this.platformType = platformType;
    }

    public static Platform of(String platformTheme, String platformUrl,
                              String platformActivityName, String platformType) {
        return new Platform(
                PlatformTheme.create(platformTheme),
                PlatformUrl.create(platformUrl),
                PlatformActivityName.create(platformActivityName),
                PlatformType.create(platformType));
    }

    public String getPlatformUrl() {
        return platformUrl.getUrl();
    }

    public String getPlatformActivityName() {
        return platformActivityName.getName();
    }

    public String getPlatformTheme() {
        return platformTheme.getTheme();
    }

    public String getPlatformType() {
        return platformType.getPlatformName();
    }

}
