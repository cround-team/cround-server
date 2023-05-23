package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidPlatformNameException extends BusinessException {

    public InvalidPlatformNameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
