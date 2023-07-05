package croundteam.cround.sample;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.*;

public class UrlTest {

    @Test
    void UrlTest1() {
        assertThat(isValidUrl("https://www.youtube.com/watch?v=FQlZ1lK7DMw")).isTrue();
        assertThat(isValidUrl("http:/www.youtube.com/watch?v=FQlZ1lK7DMw")).isTrue();
        assertThat(isValidUrl("http:/www.youtube.com")).isTrue();
        assertThat(isValidUrl("http://www.youtube.m")).isTrue();
        assertThat(isValidUrl("http://w.youtube.m")).isTrue();
        assertThat(isValidUrl("http://youtube")).isTrue();

        assertThat(isValidUrl("htps://www.youtube.com/watch?v=FQlZ1lK7DMw")).isFalse();
        assertThat(isValidUrl("youtube.com/hello")).isFalse();
        assertThat(isValidUrl("http:// youtube")).isFalse();
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
