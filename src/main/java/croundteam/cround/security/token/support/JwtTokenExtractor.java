package croundteam.cround.security.token.support;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.security.InvalidBearerTokenException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static croundteam.cround.security.token.support.TokenProvider.AUTHORIZATION;
import static croundteam.cround.security.token.support.TokenProvider.BEARER;

public class JwtTokenExtractor {

    public static String extract(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        validateBearerToken(token);
        return token.substring(BEARER.length());
    }

    private static void validateBearerToken(String token) {
        if(Objects.isNull(token) || !token.toLowerCase().startsWith(BEARER.toLowerCase())) {
            throw new InvalidBearerTokenException(ErrorCode.INVALID_BEARER_TOKEN);
        }
    }
}
