package croundteam.cround.member.domain.interest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @deprecated since 2023.05.14
 * 입력 테마 항목을 제외하기로 결정
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Deprecated(since = "2023.05.14")
public class ThemeKind {

    @Enumerated(EnumType.STRING)
    private Theme theme;

    private ThemeKind(Theme theme) {
        this.theme = theme;
    }

    public static ThemeKind from(String themeName) {
        return new ThemeKind(Theme.from(themeName));
    }
}
