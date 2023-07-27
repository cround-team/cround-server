package croundteam.cround.message.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.message.application.dto.DetailMessageResponses;
import croundteam.cround.message.application.dto.FindMessageResponses;
import croundteam.cround.message.application.dto.MessageSaveRequest;
import croundteam.cround.message.domain.Message;
import croundteam.cround.message.domain.MessageRepository;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    /**
     * case 1: 회원 -> 크리에이터 쪽지
     * case 2: 크리에이터 -> 회원 쪽지
     */
    @Transactional
    public Long saveMessage(LoginMember loginMember, MessageSaveRequest messageSaveRequest) {
        Member sender = findMemberByEmail(loginMember.getEmail());
        Member receiver = findMemberById(messageSaveRequest.getReceiver());

        Message message = new Message(sender, receiver, messageSaveRequest.getText());

        Message saveMessage = messageRepository.save(message);
        return saveMessage.getId();
    }

    public FindMessageResponses findMessages(LoginMember loginMember) {
        Member member = findMemberByEmail(loginMember.getEmail());

        List<Message> messages = messageRepository.findMessageByMember(member);

        return new FindMessageResponses(member, messages);
    }

    @Transactional
    public DetailMessageResponses findMessage(Long memberId, LoginMember loginMember) {
        Member sender = findMemberByEmail(loginMember.getEmail());
        Member receiver = findMemberById(memberId);

        List<Message> messages = messageRepository.findMessageBySenderAndReceiver(sender, receiver);
        messageRepository.updateMessageReadStatusByReceiver(sender, receiver);

        return new DetailMessageResponses(messages, sender, receiver);
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> {
            throw new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER);
        });
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER);
        });
    }
}
