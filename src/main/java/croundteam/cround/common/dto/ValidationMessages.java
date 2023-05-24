package croundteam.cround.common.dto;

public final class ValidationMessages {
    private ValidationMessages() {}

    public static final String EMPTY_MESSAGE = "비어있는 항목을 입력해주세요.";
    public static final String EMAIL_FORMAT_MESSAGE = "이메일 형식이 올바르지 않습니다.";
    public static final String MEMBER_NAME_MESSAGE = "이름은 2~6글자의 한글만 가능합니다.";
    public static final String MEMBER_EMAIL_MESSAGE = "이메일에 특수문자 '<', '>'를 포함할 수 없습니다.";
    public static final String MEMBER_NICKNAME_MESSAGE = "닉네임은 2~6글자로 영어, 한글, 숫자만 가능합니다.";

    public static final String MEMBER_NAME_FORMAT = "^[^a-zA-Z]{2,6}$";
    public static final String MEMBER_EMAIL_FORMAT = "^[^<>]*$";
    public static final String MEMBER_NICKNAME_FORMAT = "^[a-zA-Z가-힣0-9]{2,6}$";


}
