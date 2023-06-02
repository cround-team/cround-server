package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Platform {

    @Embedded
    private PlatformType platformHeadType;

    @Embedded
    private PlatformTheme platformHeadTheme;


    @Embedded
    private PlatformUrl platformUrl;

    @Embedded
    private PlatformActivityName platformActivityName;

    private Platform(PlatformTheme platformHeadTheme, PlatformType platformHeadType,
                     PlatformUrl platformUrl, PlatformActivityName platformActivityName) {
        this.platformHeadType = platformHeadType;
        this.platformHeadTheme = platformHeadTheme;
        this.platformUrl = platformUrl;
        this.platformActivityName = platformActivityName;
    }

    public static Platform of(String platformHeadTheme, String platformHeadType,
                              String platformUrl, String platformActivityName) {
        return new Platform(
                PlatformTheme.create(platformHeadTheme),
                PlatformType.create(platformHeadType),
                PlatformUrl.create(platformUrl),
                PlatformActivityName.create(platformActivityName));
    }

    public String getPlatformTheme() {
        return platformHeadTheme.getTheme();
    }

    public String getPlatformType() {
        return platformHeadType.getPlatformName();
    }

    public String getPlatformUrl() {
        return platformUrl.getUrl();
    }

    public String getPlatformActivityName() {
        return platformActivityName.getName();
    }
}
