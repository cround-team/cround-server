package croundteam.cround.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

import static croundteam.cround.common.fixtures.ConstantFixtures.PASSWORD_CHANGE_SUBJECT_MESSAGE;

public class MailTypeHelper {

    private MailTypeHelper() {
    }

    private static final String TARGET = "https://cround-client.vercel.app/password/new";

    private static final String PREFIX = "<h2>비밀번호 재설정 링크를 보내드립니다.</h2><br/><a href='" + TARGET + "?id=";
    private static final String INFIX = "&code=";
    private static final String SUFFIX = "'>비밀번호 재설정하기</a><br/>";

    public static MailSendType getMailType(String type) {
        return MailSendType.valueOf(type.toUpperCase());
    }

    @Getter
    @AllArgsConstructor
    public enum MailSendType {

        PASSWORD(PASSWORD_CHANGE_SUBJECT_MESSAGE, (id, code) -> PREFIX + id + INFIX + code + SUFFIX);

        private final String text;
        private BiFunction<Long, String, String> expression;

        public String appendText(Long id, String code) {
            return expression.apply(id, code);
        }
    }
}
