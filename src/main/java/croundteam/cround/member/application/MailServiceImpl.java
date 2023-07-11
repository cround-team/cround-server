package croundteam.cround.member.application;

import croundteam.cround.support.search.SearchCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(String email, String type) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        System.out.println("mimeMessage = " + mimeMessage);

        MimeMessageHelper helper = getMimeMessage(mimeMessage ,email, type);

        javaMailSender.send(mimeMessage);
    }

    private MimeMessageHelper getMimeMessage(MimeMessage mimeMessage, String email, String type) {
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(SubjectType.getText(type));
            mimeMessageHelper.setText("<h1>이메일 전송 테스트</h1>", true);

            return mimeMessageHelper;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum SubjectType {
        PASSWORD("[크라운드] 비밀번호 찾기 링크입니다.");

        private String text;

        public static String getText(String type) {
            return SubjectType.valueOf(type.toUpperCase()).getText();
        }
    }
}
