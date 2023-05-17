package croundteam.cround.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {

    @Column(name = "title", nullable = false)
    private String value;

    private Title(String value) {
        validateTitleLength(value);
        this.value = value;
    }

    public static Title from(String value) {
        return new Title(value);
    }

    private void validateTitleLength(String value) {
    }
}
