package croundteam.cround.board.service.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardsResponse {

    private List<BoardResponse> boardResponses;

    public BoardsResponse(List<BoardResponse> boardResponses) {
        this.boardResponses = boardResponses;
    }
}
