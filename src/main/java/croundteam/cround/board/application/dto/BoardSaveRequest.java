package croundteam.cround.board.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveRequest {
    private String platformType;
    private String title;
    private String content;
}
