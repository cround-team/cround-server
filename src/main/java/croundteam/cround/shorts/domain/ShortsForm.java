package croundteam.cround.shorts.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsForm {

    @Column(name = "shorts_url", nullable = false)
    private String shortsUrl;

    private ShortsForm(String shortsUrl) {
        this.shortsUrl = shortsUrl;
    }

    public static ShortsForm create(String shortsUrl) {
        return new ShortsForm(shortsUrl);
    }
}
