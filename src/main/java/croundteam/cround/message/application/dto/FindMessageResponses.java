package croundteam.cround.message.application.dto;

import croundteam.cround.message.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class FindMessageResponses {

    private List<FindMessageResponse> messages = new ArrayList<>();

    public FindMessageResponses(List<Message> messages) {
        this.messages = messages.stream().map(FindMessageResponse::new).collect(Collectors.toList());
    }
}
