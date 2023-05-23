package croundteam.cround.common.exception.like;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidLikeException extends BusinessException {
    public InvalidLikeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
