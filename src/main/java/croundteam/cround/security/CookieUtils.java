package croundteam.cround.security;

import croundteam.cround.security.token.support.JwtTokenExtractor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.ResponseCookie;

public class CookieUtils {

    private static final String COOKIE_NAME = "refreshToken";
    private static final int REFRESH_TOKEN_EXPIRE_AGE = 7 * 24 * 60 * 60;

    private CookieUtils() {}

    public static ResponseCookie create(String refreshToken) {
        String token = JwtTokenExtractor.extract(refreshToken);

        return ResponseCookie.from(COOKIE_NAME, token)
                .httpOnly(true)
                .path("/")
                .maxAge(REFRESH_TOKEN_EXPIRE_AGE)
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .build();
    }
}
