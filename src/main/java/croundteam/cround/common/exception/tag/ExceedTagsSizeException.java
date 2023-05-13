package croundteam.cround.common.exception.tag;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class ExceedTagsSizeException extends BusinessException {
    public ExceedTagsSizeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
