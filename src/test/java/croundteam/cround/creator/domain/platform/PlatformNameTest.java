package croundteam.cround.creator.domain.platform;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.exception.InvalidPlatformNameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class PlatformNameTest {

    @DisplayName("입력한 플랫폼 타입이 나온다.")
    @ParameterizedTest
    @CsvSource({
            "YOUTUBE, 유튜브",
            "INSTAGRAM, 인스타그램",
            "TIKTOK, 틱톡",
            "PODCAST, 팟캐스트"
    })
    void platformName(PlatformName actual, String expect) {
        assertThat(actual.getName()).isEqualTo(expect);
    }

    @Test
    @DisplayName("입력한 플랫폼 타입을 PlatformName 으로 변환한다.")
    void castPlatformNameFromString() {
        String youtube1 = "Youtube";
        String youtube2 = "yoUTuBE";

        PlatformName platformName1 = PlatformName.from(youtube1);
        PlatformName platformName2 = PlatformName.from(youtube2);

        assertThat(platformName1.getName()).isEqualTo("유튜브");
        assertThat(platformName2.getName()).isEqualTo("유튜브");
    }

    @Test
    @DisplayName("입력한 플랫폼 타입이 유효하지 않으면 에러를 반환한다.")
    void invalidInputPlatform() {
        String message = ErrorCode.INVALID_PLATFORM_NAME.getMessage();

        String cround = "cround";

        Assertions.assertThatThrownBy(() -> PlatformName.from(cround))
                .isInstanceOf(InvalidPlatformNameException.class)
                .hasMessage(message);
    }
}