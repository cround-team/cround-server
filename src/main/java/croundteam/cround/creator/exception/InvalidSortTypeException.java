package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidSortTypeException extends BusinessException {
    public InvalidSortTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
