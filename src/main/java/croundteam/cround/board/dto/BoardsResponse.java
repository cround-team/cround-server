package croundteam.cround.board.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardsResponse {

    private List<BoardResponse> boardResponses = new ArrayList<>();

    public BoardsResponse(List<BoardResponse> boardResponses) {
        this.boardResponses = boardResponses;
    }
}
