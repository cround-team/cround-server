package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class IncorrectSourceException extends BusinessException {
    public IncorrectSourceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
