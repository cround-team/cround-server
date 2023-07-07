package croundteam.cround.common.fixtures;

public final class ValidationMessages {
    private ValidationMessages() {}

    public static final String EMPTY_MESSAGE = "비어있는 항목을 입력해주세요.";
    public static final String EMAIL_FORMAT_MESSAGE = "이메일 형식이 올바르지 않습니다.";
    public static final String MEMBER_NAME_MESSAGE = "이름은 2~6글자의 한글만 가능합니다.";
    public static final String MEMBER_EMAIL_MESSAGE = "이메일에 특수문자 '<', '>'를 포함할 수 없습니다.";
    public static final String MEMBER_NICKNAME_MESSAGE = "닉네임은 2~6글자로 영어, 한글, 숫자만 가능합니다.";
    public static final String MEMBER_PASSWORD_MESSAGE = "비밀번호는 영어 대소문자, 숫자, 특수문자를 포함한 8~20자만 가능합니다.";
    public static final String MESSAGE_TEXT_EMPTY_MESSAGE = "쪽지 내용은 공백일 수 없습니다.";
    public static final String MESSAGE_RECEIVER_EMPTY_MESSAGE = "쪽지의 대상은 공백일 수 없습니다.";

    public static final String MEMBER_NAME_FORMAT = "^[^a-zA-Z]{2,6}$";
    public static final String MEMBER_EMAIL_FORMAT = "^[^<>]*$";
    public static final String MEMBER_PASSWORD_FORMAT = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?!.*\\s).{8,20}$";
    public static final String MEMBER_NICKNAME_FORMAT = "^[a-zA-Z가-힣0-9]{2,6}$";

}
