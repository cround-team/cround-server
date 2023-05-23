package croundteam.cround.board.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidLikeException extends BusinessException {
    public InvalidLikeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
