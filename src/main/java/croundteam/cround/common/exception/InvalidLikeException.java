package croundteam.cround.common.exception;

public class InvalidLikeException extends BusinessException {
    public InvalidLikeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
