package croundteam.cround.security.support;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.security.token.TokenProvider;
import croundteam.cround.security.token.exception.InvalidBearerTokenException;

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
