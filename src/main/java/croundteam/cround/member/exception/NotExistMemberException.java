package croundteam.cround.member.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class NotExistMemberException extends BusinessException {
    public NotExistMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
