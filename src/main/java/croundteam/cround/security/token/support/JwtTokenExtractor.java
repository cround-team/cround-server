package croundteam.cround.security.token.support;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.security.InvalidBearerTokenException;

import java.util.Objects;

import static croundteam.cround.security.token.support.TokenProvider.BEARER;

public class JwtTokenExtractor {

    public static String extract(String token) {
        validateBearerToken(token);
        return token.substring(BEARER.length());
    }

    private static void validateBearerToken(String token) {
        if(Objects.isNull(token) || !token.toLowerCase().startsWith(BEARER.toLowerCase())) {
            throw new InvalidBearerTokenException(ErrorCode.INVALID_BEARER_TOKEN);
        }
    }
}
