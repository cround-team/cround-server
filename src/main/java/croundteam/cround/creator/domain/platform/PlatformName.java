package croundteam.cround.creator.domain.platform;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PlatformName {
    YOUTUBE("유튜브"),
    INSTAGRAM("인스타그램"),
    TIKTOK("틱톡"),
    BLOG("블로그"),
    PODCAST("팟캐스트");

    private String title;
}
