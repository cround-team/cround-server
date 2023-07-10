package croundteam.cround.message.application.dto;

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
public class SearchMessageResponse {

    private Map<LocalDate, List<FindMessageResponse>> messages;
    private Long sender;
    private Long receiver;

    public SearchMessageResponse(List<Message> messages, Long sender, Long receiver) {
        this.messages = getOrEmpty(messages);
        this.sender = sender;
        this.receiver = receiver;
    }

    private Map<LocalDate, List<FindMessageResponse>> getOrEmpty(List<Message> messages) {
        if (CollectionUtils.isEmpty(messages)) {
            return Collections.emptyMap();
        }
        return messages.stream()
                .map(FindMessageResponse::new)
                .collect(groupingBy(FindMessageResponse::convertUpdatedDateToLocalDate));
    }
}
