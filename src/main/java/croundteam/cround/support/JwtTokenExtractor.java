package croundteam.cround.support;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.security.exception.InvalidBearerTokenException;

import java.util.Objects;

public class JwtTokenExtractor {

    public static String extract(String token) {
        validateBearerToken(token);
        return token.substring(TokenProvider.BEARER.length());
    }

    private static void validateBearerToken(String token) {
        if(Objects.isNull(token) || !token.toLowerCase().startsWith(TokenProvider.BEARER.toLowerCase())) {
            throw new InvalidBearerTokenException(ErrorCode.INVALID_AUTHENTICATION);
        }
    }
}
