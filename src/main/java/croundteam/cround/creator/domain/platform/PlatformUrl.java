package croundteam.cround.creator.domain.platform;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.exception.InvalidUrlFormatException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformUrl {

    @Column(name = "platform_url")
    private String url;

    private PlatformUrl(String url) {
        validateUrl(url);
        this.url = url;
    }

    private void validateUrl(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException ex) {
            throw new InvalidUrlFormatException(ErrorCode.INVALID_URI_FORMAT);
        }
    }

    public static PlatformUrl from(String url) {
        return new PlatformUrl(url);
    }
}
