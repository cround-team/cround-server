package croundteam.cround.creator.service.dto;

import croundteam.cround.creator.domain.Creator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchCreatorResponses {

    private List<SearchCreatorResponse> pages;
    private boolean hasNext;
    private int pageCount;

    public SearchCreatorResponses(Slice<Creator> creators) {
        this.pages = creators.map(SearchCreatorResponse::from).getContent();
        this.hasNext = creators.hasNext();
        this.pageCount = creators.getNumberOfElements();
    }
}
