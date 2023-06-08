package croundteam.cround.review.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidContentLengthException extends BusinessException {
    public InvalidContentLengthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
