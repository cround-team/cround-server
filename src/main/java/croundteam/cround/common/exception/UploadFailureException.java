package croundteam.cround.common.exception;

public class UploadFailureException extends BusinessException {
    public UploadFailureException(ErrorCode errorCode) {
        super(errorCode);
    }
}
