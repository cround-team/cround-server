package croundteam.cround.support.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchCondition extends BaseSearchCondition {

    private String keyword;
    private List<String> filter = new ArrayList<>();
    private String sort;

    public List<String> getFilter() {
        if (CollectionUtils.isEmpty(filter)) {
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
            if (!StringUtils.hasText(sort)) {
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
            if (!StringUtils.hasText(sort)) {
                return LATEST;
            }
            return ContentSortCondition.valueOf(sort.toUpperCase());
        }
    }
}
