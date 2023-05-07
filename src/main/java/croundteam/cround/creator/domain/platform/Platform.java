package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.Getter;
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

    public Platform(PlatformUrl platformUrl, PlatformType platformType) {
        this.platformUrl = platformUrl;
        this.platformType = platformType;
    }
}
