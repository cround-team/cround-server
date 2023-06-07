package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidImageExtensionException extends BusinessException {
    public InvalidImageExtensionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
