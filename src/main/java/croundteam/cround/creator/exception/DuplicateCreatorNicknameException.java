package croundteam.cround.creator.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class DuplicateCreatorNicknameException extends BusinessException {
    public DuplicateCreatorNicknameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
