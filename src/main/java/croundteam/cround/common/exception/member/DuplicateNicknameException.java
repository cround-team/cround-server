package croundteam.cround.common.exception.member;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class DuplicateNicknameException extends BusinessException {
    public DuplicateNicknameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
