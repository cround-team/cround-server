package croundteam.cround.common.dto;

import lombok.*;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class SearchCondition {
    private final int DEFAULT_PAGE_SIZE = 5;

    private String keyword;
    private List<String> filter;
    private String sort;
    private Long cursorId;
    private int size = DEFAULT_PAGE_SIZE;

    public List<String> getFilter() {
        if(Objects.isNull(filter) || filter.isEmpty()) {
            return Collections.emptyList();
        }
        return filter;
    }

    public CreatorSortCondition getSortTypeByCreator() {
        return CreatorSortCondition.getMatchedSortCondition(sort);
    }

    public ContentSortCondition getSortTypeByContent() {
        return ContentSortCondition.getMatchedSortCondition(sort);
    }

    @Getter
    @AllArgsConstructor
    public enum CreatorSortCondition {
        LATEST, FOLLOW, REVIEW;

        public static CreatorSortCondition getMatchedSortCondition(String sort) {
            if(!StringUtils.hasText(sort)) {
                return LATEST;
            }
            return CreatorSortCondition.valueOf(sort.toUpperCase());
        }
    }

    @Getter
    @AllArgsConstructor
    public enum ContentSortCondition {
        LATEST, LIKE, BOOKMARK;

        public static ContentSortCondition getMatchedSortCondition(String sort) {
            if(!StringUtils.hasText(sort)) {
                return LATEST;
            }
            return ContentSortCondition.valueOf(sort.toUpperCase());
        }
    }
}
