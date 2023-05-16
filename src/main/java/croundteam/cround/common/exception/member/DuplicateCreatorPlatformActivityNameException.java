package croundteam.cround.common.exception.member;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class DuplicateCreatorPlatformActivityNameException extends BusinessException {
    public DuplicateCreatorPlatformActivityNameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
