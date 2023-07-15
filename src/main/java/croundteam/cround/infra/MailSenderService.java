package croundteam.cround.infra;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.EmailSendException;
import croundteam.cround.member.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static croundteam.cround.infra.MailTypeHelper.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderService implements MailService {



    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void send(String email, String type) {
        Member member = findMemberByEmail(email);
        member.issueAuthorizationCode();

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
            MailSendType mailType = getMailType(type);

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(member.getEmail());
            mimeMessageHelper.setSubject(mailType.getText());
            mimeMessageHelper.setText(mailType.appendText(member.getId(), member.getAuthorizationCode()), true);
        } catch (MessagingException e) {
            log.error("[MailService.send()] 메일 전송 실패");
            throw new EmailSendException(ErrorCode.EMAIL_SEND);
        }
    }

}
