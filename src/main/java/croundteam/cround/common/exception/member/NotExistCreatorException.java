package croundteam.cround.common.exception.member;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class NotExistCreatorException extends BusinessException {
    public NotExistCreatorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
