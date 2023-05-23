package croundteam.cround.member.domain.interest;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.exception.InvalidThemeNameException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

@AllArgsConstructor
@Getter
@Deprecated(since = "2023.05.14")
public enum Theme {
    ART("예술"),
    COOK("요리"),
    BEAUTY("뷰티"),
    STUDY("학습"),
    FASHION("패션"),
    GAME("게임"),
    MUSIC("음악"),
    FINANCE("금융");

    @Column(name = "theme_name")
    private String name;

    public static Theme from(String themeName) {
        try {
            String type = themeName.trim().toUpperCase();
            return Theme.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new InvalidThemeNameException(ErrorCode.INVALID_THEME_NAME);
        }
    }
}
