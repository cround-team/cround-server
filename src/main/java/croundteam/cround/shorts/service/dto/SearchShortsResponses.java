package croundteam.cround.shorts.service.dto;

import croundteam.cround.creator.service.dto.SearchCreatorResponse;
import croundteam.cround.shorts.domain.Shorts;
import org.springframework.data.domain.Slice;

import java.util.List;

public class SearchShortsResponses {

    private List<SearchShortsResponse> pages;
    private boolean hasNext;
    private int pageCount;

    public SearchShortsResponses(Slice<Shorts> shorts) {
        this.pages = shorts.map(SearchShortsResponse::from).getContent();
        this.hasNext = shorts.hasNext();
        this.pageCount = shorts.getNumberOfElements();
    }
}
