package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformActivityName {

    @Column(name = "platform_activity_name")
    private String name;

    private PlatformActivityName(String name) {
        this.name = name;
    }

    public static PlatformActivityName from(String name) {
        validateName();
        return new PlatformActivityName(name);
    }

    /**
     * TODO: 금칙어 체크(Black-List) 로직
     */
    private static void validateName() {

    }
}
