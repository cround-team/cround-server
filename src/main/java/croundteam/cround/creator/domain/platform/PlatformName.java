package croundteam.cround.creator.domain.platform;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.tag.InvalidPlatformNameException;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public enum PlatformName {
    YOUTUBE("유튜브"),
    INSTAGRAM("인스타그램"),
    TIKTOK("틱톡"),
    PODCAST("팟캐스트");

    @Column(name = "platform_name")
    private String name;

    PlatformName(String name) {
        this.name = name;
    }

    public static PlatformName from(String platformName) {
        try {
            String type = platformName.trim().toUpperCase();
            return PlatformName.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidPlatformNameException(ErrorCode.INVALID_PLATFORM_NAME);
        }
    }
}
