package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidDescriptionException extends BusinessException {
    public InvalidDescriptionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
