package croundteam.cround.member.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.exception.EmailSendException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static croundteam.cround.common.fixtures.ConstantFixtures.PASSWORD_CHANGE_SUBJECT_MESSAGE;
import static croundteam.cround.common.fixtures.ConstantFixtures.PASSWORD_CHANGE_TEXT_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.from}")
    private String sender;

    private final JavaMailSender javaMailSender;

    @Override
    public void send(String email, String type) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        setMimeMessage(mimeMessage, email, type);

        javaMailSender.send(mimeMessage);
    }

    private void setMimeMessage(MimeMessage mimeMessage, String email, String type) {
        try {
            MailType mailType = MailType.getMailType(type);

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(mailType.getText());
            mimeMessageHelper.setText(mailType.getMessage(), true);
        } catch (MessagingException e) {
            log.error("[MailService.send()] 메일 전송 실패");
            throw new EmailSendException(ErrorCode.EMAIL_SEND);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum MailType {

        PASSWORD(PASSWORD_CHANGE_SUBJECT_MESSAGE, PASSWORD_CHANGE_TEXT_MESSAGE);

        private final String text;
        private final String message;

        public static MailType getMailType(String type) {
            return MailType.valueOf(type.toUpperCase());
        }
    }
}
