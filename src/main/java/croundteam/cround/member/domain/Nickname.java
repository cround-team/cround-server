package croundteam.cround.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    @Column(name = "nickname")
    private String name;

    public Nickname(String name) {
        validateNickname(name);
        this.name = name;
    }

    @JsonCreator
    public static Nickname create(String name) {
        return new Nickname(name);
    }

    /**
     * 금칙어
     */
    private void validateNickname(String name) {

    }
}
