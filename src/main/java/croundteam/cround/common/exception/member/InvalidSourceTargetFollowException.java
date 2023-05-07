package croundteam.cround.common.exception.member;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidSourceTargetFollowException extends BusinessException {
    public InvalidSourceTargetFollowException(ErrorCode errorCode) {
        super(errorCode);
    }
}
