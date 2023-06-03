package croundteam.cround.shorts.service.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.shorts.domain.Shorts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchShortsResponses {

    private List<SearchShortsResponse> pages;
    private boolean hasNext;
    private int pageCount;

    public SearchShortsResponses(Slice<Shorts> shorts, Member member) {
        this.pages = shorts.map(s -> SearchShortsResponse.from(s, member)).getContent();
        this.hasNext = shorts.hasNext();
        this.pageCount = shorts.getNumberOfElements();
    }
}
