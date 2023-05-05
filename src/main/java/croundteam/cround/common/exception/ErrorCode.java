package croundteam.cround.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 E-mail입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다. 다시 확인해주세요.");

    private final HttpStatus status;
    private final String message;


}
