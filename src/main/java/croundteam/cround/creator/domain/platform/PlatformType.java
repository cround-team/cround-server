package croundteam.cround.creator.domain.platform;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    private PlatformType(PlatformName platformName) {
        this.platformName = platformName;
    }

    @JsonCreator
    public static PlatformType from(String platformName) {
        return new PlatformType(PlatformName.from(platformName));
    }

    public static PlatformType from(PlatformName platformName) {
        return new PlatformType(platformName);
    }

    public String getPlatformName() {
        return platformName.getName();
    }
}
