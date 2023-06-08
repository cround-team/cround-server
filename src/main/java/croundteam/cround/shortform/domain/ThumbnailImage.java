package croundteam.cround.shortform.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThumbnailImage {

    @Column(name = "thumbnail_url", nullable = false)
    private String url;

    private ThumbnailImage(String imageUrl) {
        this.url = imageUrl;
    }

    public static ThumbnailImage create(String shortsUrl) {
        return new ThumbnailImage(shortsUrl);
    }

    public String getThumbnailImage() {
        return url;
    }
}
