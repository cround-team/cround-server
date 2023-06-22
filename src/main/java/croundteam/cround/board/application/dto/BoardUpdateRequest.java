package croundteam.cround.board.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequest {

    private String title;
    private String content;
    private String platformType;
}
