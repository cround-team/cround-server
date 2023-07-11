package croundteam.cround.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 400 Bad Request
     */
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 E-mail입니다."),
    DUPLICATE_LIKE(HttpStatus.BAD_REQUEST, "이미 좋아요한 대상입니다."),
    DUPLICATE_BOOKMARK(HttpStatus.BAD_REQUEST, "이미 북마크한 대상입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),

    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    PROFILE_IMAGE_MATCH(HttpStatus.BAD_REQUEST, "설정된 프로필 이미지와 달라야 합니다."),
    PROFILE_IMAGE_EMPTY(HttpStatus.BAD_REQUEST, "입력된 프로필 이미지가 없습니다."),

    EXCEED_TAGS_SIZE(HttpStatus.BAD_REQUEST, "태그는 최소 1개, 최대 7개까지 설정할 수 있습니다."),
    EXCEED_TAG_LENGTH(HttpStatus.BAD_REQUEST, "태그는 최대 20글자까지 입력할 수 있습니다."),
    EXCEED_THEME_LENGTH(HttpStatus.BAD_REQUEST, "대표 테마는 최대 10글자까지 입력 가능합니다."),
    EXCEED_RATING_RANGE(HttpStatus.BAD_REQUEST, "별점은 1~5개만 가능합니다."),

    EMPTY_DESCRIPTION(HttpStatus.BAD_REQUEST, "소개는 공백일 수 없습니다."),

    NOT_EMPTY_TAG(HttpStatus.BAD_REQUEST, "태그는 최소 1개 이상 설정돼야 합니다."),

    INVALID_SORT_TYPE(HttpStatus.BAD_REQUEST, "존재하지 않는 정렬 타입입니다."),
    INVALID_PLATFORM_TYPE(HttpStatus.BAD_REQUEST, "존재하지 않는 플랫폼 유형입니다."),
    INVALID_URI_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 URL 입니다."),
    INVALID_PROVIDER_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 Provider입니다."),
    INVALID_IMAGE_EXTENSION(HttpStatus.BAD_REQUEST, "유효하지 않은 확장자입니다."),
    INVALID_CONTENT_LENGTH(HttpStatus.BAD_REQUEST, "후기는 최소 1글자 이상, 최대 1000글자까지 가능합니다."),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "쪽지를 보낼 수 없는 대상입니다."),
    DUPLICATE_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우한 대상입니다."),

    /**
     * 401 Unauthorized
     */
    INVALID_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
    INCORRECT_SOURCE(HttpStatus.UNAUTHORIZED, "동일한 요청자가 아닙니다."),
    INVALID_AUTHORIZATION_CODE(HttpStatus.UNAUTHORIZED, "올바른 사용자가 아닙니다."),

    /**
     * 403 Forbidden
     */
    INVALID_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    /**
     * 404 Not Found
     */
    NOT_EXIST_BOARD(HttpStatus.NOT_FOUND, "존재하지 않는 콘텐츠입니다."),
    NOT_EXIST_CREATOR(HttpStatus.NOT_FOUND, "존재하지 않는 크리에이터입니다."),
    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    NOT_EXIST_SHORT_FORM(HttpStatus.NOT_FOUND, "존재하지 않는 숏클래스입니다."),

    /**
     * 500 INTERNAL SERVER ERROR
     */
    UPLOAD_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 S3에 저장 실패하였습니다."),
    EMAIL_SEND(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패하였습니다.");


    private final HttpStatus status;
    private final String message;
}
