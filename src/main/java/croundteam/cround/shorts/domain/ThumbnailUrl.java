package croundteam.cround.shorts.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThumbnailUrl {

    @Column(name = "thumbnail_url", nullable = false)
    private String imageUrl;

    private ThumbnailUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ThumbnailUrl create(String shortsUrl) {
        return new ThumbnailUrl(shortsUrl);
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
