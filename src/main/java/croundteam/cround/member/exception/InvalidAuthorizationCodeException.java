package croundteam.cround.member.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidAuthorizationCodeException extends BusinessException {
    public InvalidAuthorizationCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
