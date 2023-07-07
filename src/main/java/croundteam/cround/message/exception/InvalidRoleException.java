package croundteam.cround.message.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidRoleException extends BusinessException {
    public InvalidRoleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
