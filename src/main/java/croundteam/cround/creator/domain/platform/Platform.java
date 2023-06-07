package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Platform {

    @Enumerated(EnumType.STRING)
    private PlatformType platformHeadType;

    @Embedded
    private PlatformTheme platformHeadTheme;

    @Embedded
    private PlatformUrl platformUrl;

    private Platform(PlatformTheme platformHeadTheme, PlatformType platformHeadType, PlatformUrl platformUrl) {
        this.platformHeadType = platformHeadType;
        this.platformHeadTheme = platformHeadTheme;
        this.platformUrl = platformUrl;
    }

    public static Platform of(String platformHeadTheme, String platformHeadType, String platformUrl) {
        return new Platform(
                PlatformTheme.create(platformHeadTheme),
                PlatformType.create(platformHeadType),
                PlatformUrl.create(platformUrl));
    }

    public String getPlatformTheme() {
        return platformHeadTheme.getPlatformTheme();
    }

    public String getPlatformType() {
        return platformHeadType.getPlatformType();
    }

    public String getPlatformUrl() {
        return platformUrl.getPlatformUrl();
    }
}
