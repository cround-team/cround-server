package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class ExceedPlatformThemeLengthException extends BusinessException {
    public ExceedPlatformThemeLengthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
