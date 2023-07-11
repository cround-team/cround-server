package croundteam.cround.message.application.dto;

import croundteam.cround.message.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FindMessageResponse {

    private Long id;
    private Long sender;
    private Long receiver;
    private String text;
    private LocalDateTime updatedDate;

    public FindMessageResponse(Message message) {
        this.id = message.getId();
        this.sender = message.getSender();
        this.receiver = message.getReceiver();
        this.text = message.getText();
        this.updatedDate = message.getUpdatedDate();
    }

    public LocalDate convertUpdatedDateToLocalDate() {
        return updatedDate.toLocalDate();
    }
}
