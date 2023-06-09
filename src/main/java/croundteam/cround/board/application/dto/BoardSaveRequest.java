package croundteam.cround.board.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequest {

    private String title;
    private String content;
    private String platformType;

    public BoardSaveRequest(String title, String content, String platformType) {
        this.title = title;
        this.content = content;
        this.platformType = platformType;
    }
}
