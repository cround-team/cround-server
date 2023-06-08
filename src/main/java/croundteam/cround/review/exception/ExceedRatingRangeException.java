package croundteam.cround.review.exception;

import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;

public class ExceedRatingRangeException extends BusinessException {
    public ExceedRatingRangeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
