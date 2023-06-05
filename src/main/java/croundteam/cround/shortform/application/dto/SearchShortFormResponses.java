package croundteam.cround.shortform.application.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.shortform.domain.ShortForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchShortFormResponses {

    private List<SearchShortFormResponse> pages;
    private boolean hasNext;
    private int pageCount;

    public SearchShortFormResponses(Slice<ShortForm> shorts, Member member) {
        this.pages = shorts.map(s -> SearchShortFormResponse.from(s, member)).getContent();
        this.hasNext = shorts.hasNext();
        this.pageCount = shorts.getContent().size();
    }
}
