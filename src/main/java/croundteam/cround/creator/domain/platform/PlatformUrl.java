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

    public PlatformUrl(String url) {
        this.url = url;
    }
}
