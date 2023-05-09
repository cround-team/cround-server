package croundteam.cround.common.advice;

import croundteam.cround.common.dto.ErrorResponse;
import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("parameter = {}, message = {}", e.getParameter().getParameterName(), e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.info("error code = {}, message = {}", errorCode.getStatus(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(new ErrorResponse(errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        /**
         * Slack or Discord 로 Notification 추가
         */
        log.info("500: Internal server error, message = {}", e.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorResponse("알 수 없는 문제가 발생했습니다. 서버 관리자에게 문의주세요."));
    }
}
