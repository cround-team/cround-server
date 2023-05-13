package croundteam.cround.member.domain.interest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
