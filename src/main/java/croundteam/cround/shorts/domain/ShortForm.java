package croundteam.cround.shorts.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortForm {

    @Column(name = "shorts_url", nullable = false)
    private String shortsUrl;

    private ShortForm(String shortsUrl) {
        this.shortsUrl = shortsUrl;
    }

    public static ShortForm from(String shortsUrl) {
        return new ShortForm(shortsUrl);
    }
}
