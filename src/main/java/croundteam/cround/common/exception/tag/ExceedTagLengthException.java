package croundteam.cround.common.exception.tag;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class ExceedTagLengthException extends BusinessException {
    public ExceedTagLengthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
