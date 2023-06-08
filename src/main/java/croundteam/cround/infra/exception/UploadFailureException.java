package croundteam.cround.infra.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class UploadFailureException extends BusinessException {
    public UploadFailureException(ErrorCode errorCode) {
        super(errorCode);
    }
}
