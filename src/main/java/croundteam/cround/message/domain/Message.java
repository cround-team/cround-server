package croundteam.cround.message.domain;

import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.member.domain.Member;
import croundteam.cround.support.DateConverter;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member receiver;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

    public Message(Member sender, Member receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.readStatus = ReadStatus.UNREAD;
    }

    public String getFormatUpdatedDate() {
        return DateConverter.convertToFormat(getUpdatedDate());
    }

    public String getReceiverName() {
        return receiver.getNickname();
    }

    public String getSenderName() {
        return sender.getNickname();
    }

    public Long getSenderId() {
        return sender.getId();
    }

    public String getReadStatus() {
        return readStatus.getStatus();
    }
}
