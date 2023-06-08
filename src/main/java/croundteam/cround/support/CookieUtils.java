package croundteam.cround.support;

import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.boot.web.server.Cookie.*;

public class CookieUtils {

    private static final String COOKIE_NAME = "refreshToken";
    private static final int REFRESH_TOKEN_EXPIRE_AGE = 7 * 24 * 60 * 60;

    private CookieUtils() {}

    public static ResponseCookie create(String refreshToken) {
        return ResponseCookie.from(COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(REFRESH_TOKEN_EXPIRE_AGE)
                .secure(true)
                .sameSite(SameSite.NONE.attributeValue())
                .build();
    }

    public static String extractTokenByCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
