package croundteam.cround.message.application.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.message.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Getter
@NoArgsConstructor
public class DetailMessageResponses {

    private Map<LocalDate, List<DetailMessageResponse>> messages;
    private Long sender;
    private Long receiver;
    private String nickname;

    public DetailMessageResponses(List<Message> messages, Member sender, Member receiver) {
        this.messages = getOrEmpty(messages);
        this.sender = sender.getId();
        this.receiver = receiver.getId();
        this.nickname = receiver.getNickname();
    }

    private Map<LocalDate, List<DetailMessageResponse>> getOrEmpty(List<Message> messages) {
        if (CollectionUtils.isEmpty(messages)) {
            return Collections.emptyMap();
        }
        return messages.stream()
                .map(DetailMessageResponse::new)
                .collect(groupingBy(DetailMessageResponse::convertUpdatedDateToLocalDate));
    }
}
