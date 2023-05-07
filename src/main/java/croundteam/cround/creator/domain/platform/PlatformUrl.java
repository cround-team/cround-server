package croundteam.cround.creator.domain.platform;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformUrl {

    @Column(name = "platform_url")
    private String url;

    private PlatformUrl(String url) {
        this.url = url;
    }

    public static PlatformUrl from(String url) {
        return new PlatformUrl(url);
    }
}
