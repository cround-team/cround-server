package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class ExceedThemeLengthException extends BusinessException {
    public ExceedThemeLengthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
