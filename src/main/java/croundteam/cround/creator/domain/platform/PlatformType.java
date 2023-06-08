package croundteam.cround.creator.domain.platform;

import com.fasterxml.jackson.annotation.JsonCreator;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.exception.InvalidPlatformTypeException;

import javax.persistence.Column;

public enum PlatformType {
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

    @Column(name = "platform_type", nullable = false)
    private String type;

    PlatformType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static PlatformType create(String platformType) {
        try {
            String type = platformType.trim().toUpperCase();
            return PlatformType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidPlatformTypeException(ErrorCode.INVALID_PLATFORM_TYPE);
        }
    }

    public String getType() {
        return type;
    }
}
