package croundteam.cround.creator.domain.platform;

import com.fasterxml.jackson.annotation.JsonCreator;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.exception.InvalidPlatformNameException;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public enum PlatformName {
    YOUTUBE("유튜브"),
    INSTAGRAM("인스타그램"),
    TIKTOK("틱톡"),
    TWITCH("트위치"),
    AFREECATV("아프리카TV"),
    BLOG("블로그"),
    PODCAST("팟캐스트"),
    SPOON("스푼"),
    ZEPETTO("제페토"),
    EMOTICON("이모티콘"),
    NFT("NFT");

    @Column(name = "platform_name", nullable = false)
    private String name;

    PlatformName(String name) {
        this.name = name;
    }

    @JsonCreator
    public static PlatformName from(String platformName) {
        try {
            String type = platformName.trim().toUpperCase();
            return PlatformName.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidPlatformNameException(ErrorCode.INVALID_PLATFORM_NAME);
        }
    }
}
