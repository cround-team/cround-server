package croundteam.cround.message.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static croundteam.cround.common.fixtures.ValidationMessages.MESSAGE_RECEIVER_EMPTY_MESSAGE;
import static croundteam.cround.common.fixtures.ValidationMessages.MESSAGE_TEXT_EMPTY_MESSAGE;

@Getter
@NoArgsConstructor
public class MessageSaveRequest {

    @NotBlank(message = MESSAGE_RECEIVER_EMPTY_MESSAGE)
    private Long receiver;

    @NotBlank(message = MESSAGE_TEXT_EMPTY_MESSAGE)
    private String text;

    public MessageSaveRequest(Long receiver, String text) {
        this.receiver = receiver;
        this.text = text;
    }
}
