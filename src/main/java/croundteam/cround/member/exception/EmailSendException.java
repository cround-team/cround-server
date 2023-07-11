package croundteam.cround.member.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class EmailSendException extends BusinessException {
    public EmailSendException(ErrorCode errorCode) {
        super(errorCode);
    }
}
