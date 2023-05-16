package croundteam.cround.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 E-mail입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    DUPLICATE_PLATFORM_ACTIVITY_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 활동명입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_PLATFORM_NAME(HttpStatus.BAD_REQUEST, "존재하지 않는 플랫폼 유형입니다."),
    PROFILE_IMAGE_MATCH(HttpStatus.BAD_REQUEST, "설정된 프로필 이미지와 달라야 합니다."),
    PROFILE_IMAGE_EMPTY(HttpStatus.BAD_REQUEST, "입력된 프로필 이미지가 없습니다."),
    INVALID_SOURCE_TARGET_FOLLOW(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우 할 수 없습니다."),
    INVALID_URI_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 URL 입니다."),
    INVALID_THEME_NAME(HttpStatus.BAD_REQUEST, "유효하지 않은 테마 이름 입니다."),
    EXCEED_TAGS_MAX_SIZE(HttpStatus.BAD_REQUEST, "태그는 최대 7개까지 설정할 수 있습니다."),
    EXCEED_TAG_LENGTH(HttpStatus.BAD_REQUEST, "태그는 최대 20글자까지 입력할 수 있습니다."),

    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");

    private final HttpStatus status;
    private final String message;
}
