package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class ProfileImageEmptyException extends BusinessException {
    public ProfileImageEmptyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
