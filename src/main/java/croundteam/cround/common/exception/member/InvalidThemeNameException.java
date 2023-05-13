package croundteam.cround.common.exception.member;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class InvalidThemeNameException extends BusinessException {
    public InvalidThemeNameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
