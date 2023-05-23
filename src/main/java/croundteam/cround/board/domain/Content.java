package croundteam.cround.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    @Column(name = "content", nullable = false, columnDefinition = "text")
    @Lob
    private String value;

    private Content(String value) {
        this.value = value;
    }

    public static Content from(String value) {
        return new Content(value);
    }

}
