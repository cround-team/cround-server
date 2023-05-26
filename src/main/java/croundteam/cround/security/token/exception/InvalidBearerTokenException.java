package croundteam.cround.security.token.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidBearerTokenException extends BusinessException {
    public InvalidBearerTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
