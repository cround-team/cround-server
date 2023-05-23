package croundteam.cround.board.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class NotExistBoardException extends BusinessException {
    public NotExistBoardException(ErrorCode errorCode) {
        super(errorCode);
    }
}
