package croundteam.cround.member.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidUrlFormatException extends BusinessException {
    public InvalidUrlFormatException(ErrorCode errorCode) {
        super(errorCode);
    }
}
