package croundteam.cround.creator.domain.platform;

import croundteam.cround.member.exception.InvalidUrlFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlatformUrlTest {

    @Test
    @DisplayName("크리에이터 등록시 입력받은 플랫폼 URL이 유효한지 검사한다.")
    void validCreatorUrl() {
        String croundUrl = "http://cround.com";

        assertThat(PlatformUrl.from(croundUrl)).isNotNull();
    }

    @Test
    @DisplayName("크리에이터 등록시 플랫폼 URL이 유효하지 않으면 예외를 발생시킨다.")
    void invalidURISyntaxCreatorUrl() {
        String url = "http://cround.com/      @cround";

        assertThatThrownBy(() -> PlatformUrl.from(url))
                .isInstanceOf(InvalidUrlFormatException.class)
                .hasMessage("유효하지 않은 URL 입니다.");
    }

    @Test
    @DisplayName("크리에이터 등록시 플랫폼 URL이 올바른 프로토콜이 아니라면 예외를 발생시킨다.")
    void invalidMalformedCreatorUrl() {
        String url = "hhttpp://cround.com";

        assertThatThrownBy(() -> PlatformUrl.from(url))
                .isInstanceOf(InvalidUrlFormatException.class)
                .hasMessage("유효하지 않은 URL 입니다.");
    }
}