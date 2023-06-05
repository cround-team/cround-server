package croundteam.cround.shortform.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class NotExistShortFormException extends BusinessException {
    public NotExistShortFormException(ErrorCode errorCode) {
        super(errorCode);
    }
}
