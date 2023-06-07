package croundteam.cround.creator.domain.platform;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.exception.ExceedPlatformThemeLengthException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static croundteam.cround.common.fixtures.ConstantFixtures.CREATOR_PLATFORM_THEME_LENGTH_MAX_SIZE;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformTheme {

    @Column(name = "platform_theme")
    private String theme;

    private PlatformTheme(String theme) {
        validateThemeSize(theme);
        this.theme = theme;
    }

    public static PlatformTheme create(String platformTheme) {
        return new PlatformTheme(platformTheme);
    }

    private void validateThemeSize(String theme) {
        if (!(theme.length() < CREATOR_PLATFORM_THEME_LENGTH_MAX_SIZE)) {
            throw new ExceedPlatformThemeLengthException(ErrorCode.EXCEED_THEME_LENGTH);
        }
    }

    public String getPlatformTheme() {
        return theme;
    }
}
