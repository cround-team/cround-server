package croundteam.cround.member.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
