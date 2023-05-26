package croundteam.cround.security.token.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidProviderTypeException extends BusinessException {
    public InvalidProviderTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
