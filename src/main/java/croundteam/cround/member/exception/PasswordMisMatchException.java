package croundteam.cround.member.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class PasswordMisMatchException extends BusinessException {
    public PasswordMisMatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
