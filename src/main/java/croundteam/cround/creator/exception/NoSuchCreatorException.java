package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class NoSuchCreatorException extends BusinessException {
    public NoSuchCreatorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
