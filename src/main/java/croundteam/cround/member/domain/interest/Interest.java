package croundteam.cround.member.domain.interest;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {

    /**
     * 플랫폼 여러개
     * 테마 여러개
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "interest_platform",
            joinColumns = @JoinColumn(
                    name = "member_id"))
    private List<PlatformType> interestPlatform = new ArrayList<>();

    /**
     * Enum 확장성 있는 구조로 변경
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "interest_theme",
            joinColumns = @JoinColumn(
                    name = "member_id"))
    private List<ThemeKind> themeKinds = new ArrayList<>();

    public Interest(List<PlatformType> interestPlatform, List<ThemeKind> themeKinds) {
        this.interestPlatform = interestPlatform;
        this.themeKinds = themeKinds;
    }

    public static Interest of(List<PlatformType> platformTypes, List<ThemeKind> themes) {
        return new Interest(platformTypes, themes);
    }
}
