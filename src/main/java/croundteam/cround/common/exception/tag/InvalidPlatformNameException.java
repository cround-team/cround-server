package croundteam.cround.common.exception.tag;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidPlatformNameException extends BusinessException {

    public InvalidPlatformNameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
