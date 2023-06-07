package croundteam.cround.shortform.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThumbnailUrl {

    @Column(name = "thumbnail_url", nullable = false)
    private String url;

    private ThumbnailUrl(String imageUrl) {
        this.url = imageUrl;
    }

    public static ThumbnailUrl create(String shortsUrl) {
        return new ThumbnailUrl(shortsUrl);
    }

    public String getThumbnailUrl() {
        return url;
    }
}
