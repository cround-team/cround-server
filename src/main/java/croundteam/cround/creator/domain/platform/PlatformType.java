package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformType {

    @Enumerated(EnumType.STRING)
    private PlatformName platformName;

    public PlatformType(String platformName) {
        this(PlatformName.from(platformName));
    }

    public PlatformType(PlatformName platformName) {
        this.platformName = platformName;
    }

    public String getPlatformName() {
        return platformName.getName();
    }
}
