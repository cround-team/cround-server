package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformType {

    @Enumerated(EnumType.STRING)
    private PlatformName platformName;

    private PlatformType(PlatformName platformName) {
        this.platformName = platformName;
    }

    public static PlatformType from(String platformName) {
        return new PlatformType(PlatformName.from(platformName));
    }

    public static PlatformType from(PlatformName platformName) {
        return new PlatformType(platformName);
    }

    public static List<PlatformType> castPlatformTypeList(List<String> types) {
        return types.stream()
                .map(PlatformType::from)
                .collect(Collectors.toList());
    }

    public String getPlatformName() {
        return platformName.getName();
    }
}
