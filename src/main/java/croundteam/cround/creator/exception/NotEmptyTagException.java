package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class NotEmptyTagException extends BusinessException {
    public NotEmptyTagException(ErrorCode errorCode) {
        super(errorCode);
    }
}
