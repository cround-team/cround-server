package croundteam.cround.common.advice;

import croundteam.cround.common.dto.ErrorResponse;
import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        /**
         * Slack or Discord 로 Notification 추가
         */
        String method = request.getMethod();
        String requestURI = request.getRequestURI();

        log.info("ERROR 500: [{}][{}]: Exception Message = {}", method, requestURI, e.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorResponse("알 수 없는 문제가 발생했습니다. 서버 관리자에게 문의주세요."));
    }
}
