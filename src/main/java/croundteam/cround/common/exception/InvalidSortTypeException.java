package croundteam.cround.common.exception;

public class InvalidSortTypeException extends BusinessException {
    public InvalidSortTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
