package croundteam.cround.message.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReadStatus {
    READ("읽음"), UNREAD("읽지 않음");

    private String status;
}
