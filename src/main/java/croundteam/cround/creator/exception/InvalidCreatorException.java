package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidCreatorException extends BusinessException {
    public InvalidCreatorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
