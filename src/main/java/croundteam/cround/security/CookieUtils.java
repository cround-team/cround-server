package croundteam.cround.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class CookieUtils {

    private static final String COOKIE_NAME = "refreshToken";
    private static final String COOKIE_NAME = "refreshToken";

    private CookieUtils() {}

    public static void create(String refreshToken) {
        return ResponseCookie.from(COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge()
    }
}
