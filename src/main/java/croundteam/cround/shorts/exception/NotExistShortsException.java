package croundteam.cround.shorts.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class NotExistShortsException extends BusinessException {
    public NotExistShortsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
