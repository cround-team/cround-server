package croundteam.cround.message.domain;

import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.member.domain.Member;
import croundteam.cround.message.application.dto.MessageSaveRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long sender;

    @Column(nullable = false, updatable = false)
    private Long receiver;

    @Column(nullable = false)
    private String text;

    public Message(Long sender, Long receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public Message(Member sender, Member receiver, String text) {
        this.sender = sender.getId();
        this.receiver = receiver.getId();
        this.text = text;
    }
}
