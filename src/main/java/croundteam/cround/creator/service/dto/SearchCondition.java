package croundteam.cround.creator.service.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class SearchCondition {
    private final int DEFAULT_SIZE = 10;

    private List<String> platforms;
    private String keyword;
    private String sort;
    private int page;

    public PageRequest toPageRequest() {
        return PageRequest.of(page, DEFAULT_SIZE, CreatorSortCondition.getMatchedSortCondition(sort));
    }

    @Getter
    @AllArgsConstructor
    private enum CreatorSortCondition {
        LATEST(Sort.by(Sort.Direction.DESC, "id"));

        private final Sort sort;

        public static Sort getMatchedSortCondition(String sort) {
            if(Objects.isNull(sort) || sort.isBlank()) {
                return LATEST.getSort();
            }
            return CreatorSortCondition.valueOf(sort.toUpperCase()).getSort();
        }
    }
}
