package croundteam.cround.message.application.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.message.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindMessageResponse {

    private Long id;
    private Long sender;
    private Long receiver;
    private String text;
    private String updatedDate;
    private String nickname;

    public FindMessageResponse(Member member, Message message) {
        this.id = message.getId();
        this.sender = message.getSender().getId();
        this.receiver = message.getReceiver().getId();
        this.text = message.getText();
        this.updatedDate = message.getFormatUpdatedDate();
        this.nickname = getReceiverName(member, message);
    }

    private String getReceiverName(Member member, Message message) {
        Long senderId = message.getSenderId();
        return member.isSender(senderId) ? message.getReceiverName() : message.getSenderName();
    }
}
