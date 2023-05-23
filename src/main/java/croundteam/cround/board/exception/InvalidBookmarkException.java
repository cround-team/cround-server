package croundteam.cround.board.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidBookmarkException extends BusinessException {
    public InvalidBookmarkException(ErrorCode errorCode) {
        super(errorCode);
    }
}
