package croundteam.cround.member.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidSourceTargetFollowException extends BusinessException {
    public InvalidSourceTargetFollowException(ErrorCode errorCode) {
        super(errorCode);
    }
}
