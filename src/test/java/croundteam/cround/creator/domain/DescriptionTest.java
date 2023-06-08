package croundteam.cround.creator.domain;

import croundteam.cround.creator.exception.InvalidDescriptionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("크리에이터의")
class DescriptionTest {

    @Test
    @DisplayName("설명은 빈 값일 수 없습니다.")
    void creatorDescription_fail() {
        assertThatThrownBy(() -> Description.create(""))
                .isInstanceOf(InvalidDescriptionException.class)
                .hasMessage("소개는 공백일 수 없습니다.");
    }

}