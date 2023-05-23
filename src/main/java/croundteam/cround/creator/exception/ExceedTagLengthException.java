package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class ExceedTagLengthException extends BusinessException {
    public ExceedTagLengthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
