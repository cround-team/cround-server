package croundteam.cround.common.advice;

import croundteam.cround.common.dto.ErrorResponse;
import croundteam.cround.common.exception.BusinessException;
import croundteam.cround.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    // TODO: 프론트 님한테 입력값 정보 필요한지 회의해보기
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            builder.append("[" + fieldError.getField() + "](은)는 " + fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [" + fieldError.getRejectedValue() + "]");
        }
        return ResponseEntity.badRequest().body(new ErrorResponse(String.valueOf(builder)));
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(new ErrorResponse(errorCode.getMessage()));
    }


}
