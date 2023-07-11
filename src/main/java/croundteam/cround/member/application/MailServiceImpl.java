package croundteam.cround.member.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.EmailSendException;
import croundteam.cround.member.exception.NotExistMemberException;
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

import static croundteam.cround.common.fixtures.ConstantFixtures.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.from}")
    private String sender;

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Override
    public void send(String email, String type) {
        Member member = findMemberByEmail(email);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        setMimeMessage(mimeMessage, member, type);

        javaMailSender.send(mimeMessage);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER);
        });
    }

    private void setMimeMessage(MimeMessage mimeMessage, Member member, String type) {
        try {
            MailType mailType = MailType.getMailType(type);

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(member.getEmail());
            mimeMessageHelper.setSubject(mailType.getText());
            mimeMessageHelper.setText(appendMailTextAndId(mailType, member), true);
        } catch (MessagingException e) {
            log.error("[MailService.send()] 메일 전송 실패");
            throw new EmailSendException(ErrorCode.EMAIL_SEND);
        }
    }

    private String appendMailTextAndId(MailType mailType, Member member) {
        return mailType.getPrefix() + member.getId() + mailType.getSuffix();
    }

    @Getter
    @AllArgsConstructor
    public enum MailType {

        PASSWORD(PASSWORD_CHANGE_SUBJECT_MESSAGE, PASSWORD_CHANGE_TEXT_PREFIX, PASSWORD_CHANGE_TEXT_SUFFIX);

        private final String text;
        private final String prefix;
        private final String suffix;

        public static MailType getMailType(String type) {
            return MailType.valueOf(type.toUpperCase());
        }
    }
}
