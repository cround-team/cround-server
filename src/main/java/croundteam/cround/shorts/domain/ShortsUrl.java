package croundteam.cround.shorts.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsUrl {

    @Column(name = "shorts_url", nullable = false)
    private String url;

    private ShortsUrl(String url) {
        this.url = url;
    }

    public static ShortsUrl create(String url) {
        return new ShortsUrl(url);
    }

    public String getShortsUrl() {
        return url;
    }
}
