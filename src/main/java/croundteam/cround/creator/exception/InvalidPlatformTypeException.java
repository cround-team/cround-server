package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidPlatformTypeException extends BusinessException {

    public InvalidPlatformTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
