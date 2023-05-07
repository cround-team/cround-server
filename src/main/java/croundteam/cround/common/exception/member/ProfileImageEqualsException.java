package croundteam.cround.common.exception.member;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class ProfileImageEqualsException extends BusinessException {
    public ProfileImageEqualsException(ErrorCode errorCode) {
        super(errorCode);
    }
}