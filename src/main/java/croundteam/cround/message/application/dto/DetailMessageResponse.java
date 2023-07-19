package croundteam.cround.message.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import croundteam.cround.message.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DetailMessageResponse {

    private Long id;
    private Long sender;
    private Long receiver;
    private String text;
    private String updatedDate;
    @JsonIgnore private LocalDateTime lastModifiedDate;

    public DetailMessageResponse(Message message) {
        this.id = message.getId();
        this.sender = message.getSender().getId();
        this.receiver = message.getReceiver().getId();
        this.text = message.getText();
        this.lastModifiedDate = message.getUpdatedDate();
        this.updatedDate = message.getFormatUpdatedDate().substring(8);
    }

    public LocalDate convertUpdatedDateToLocalDate() {
        return lastModifiedDate.toLocalDate();
    }
}
