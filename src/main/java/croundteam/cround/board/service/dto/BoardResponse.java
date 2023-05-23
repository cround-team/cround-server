package croundteam.cround.board.service.dto;

import croundteam.cround.board.domain.Board;
import lombok.Getter;

@Getter
public class BoardResponse {

    private String platformType;
    private String title;
    private String content;
    private String author;

    public BoardResponse(Board board) {
        this.platformType = board.getPlatformType();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
    }
}
