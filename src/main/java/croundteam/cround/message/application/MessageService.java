package croundteam.cround.message.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.CreatorRepository;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.message.application.dto.FindMessageResponses;
import croundteam.cround.message.application.dto.MessageSaveRequest;
import croundteam.cround.message.domain.Message;
import croundteam.cround.message.domain.MessageRepository;
import croundteam.cround.message.exception.InvalidRoleException;
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
    private final CreatorRepository creatorRepository;
    private final MessageRepository messageRepository;

    /**
     * case 1: 회원 -> 크리에이터 쪽지
     * case 2: 크리에이터 -> 회원 쪽지
     */
    @Transactional
    public Long saveMessage(LoginMember loginMember, MessageSaveRequest messageSaveRequest) {
        Member sender = findMemberByEmail(loginMember.getEmail());
        Member receiver = findReceiverBy(sender, messageSaveRequest.getReceiver());

        Message message = new Message(sender, receiver, messageSaveRequest.getText());

        Message saveMessage = messageRepository.save(message);
        return saveMessage.getId();
    }

    private Member findReceiverBy(Member sender, Long id) {
        switch (sender.getRoleName()) {
            case "크리에이터":
                return findMemberById(id);
            case "회원":
                return findMemberByCreator(id);
        }
        throw new InvalidRoleException(ErrorCode.INVALID_ROLE);
    }

    public FindMessageResponses findMessages(LoginMember loginMember) {
        Member member = findMemberByEmail(loginMember.getEmail());
        List<Message> messages = messageRepository.findMessageBySender(member.getId());

        return new FindMessageResponses(messages);
    }


    private Member findMemberByCreator(Long creatorId) {
        Creator creator = creatorRepository.findCreatorById(creatorId).orElseThrow(() -> {
            throw new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR);
        });
        return creator.getMember();
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
