package croundteam.cround.board.service.dto;

import croundteam.cround.board.domain.Board;
import croundteam.cround.member.domain.Member;
import croundteam.cround.shorts.service.dto.SearchShortsResponse;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class SearchBoardsResponses {

    private List<SearchBoardsResponse> pages;
    private boolean hasNext;
    private int pageCount;

    public SearchBoardsResponses(Slice<Board> boards, Member member) {
        this.pages = boards.map(b -> SearchBoardsResponse.from(b, member)).getContent();
        this.hasNext = boards.hasNext();
        this.pageCount = boards.getNumberOfElements();
    }
}
