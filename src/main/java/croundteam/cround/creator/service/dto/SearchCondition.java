package croundteam.cround.creator.service.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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
        return PageRequest.of(page, DEFAULT_SIZE); // CreatorSortCondition.getMatchedSortCondition(sort)
    }

    public String getCondition() {
        return CreatorSortCondition.valueOf(sort.toUpperCase()).name();
    }


    @Getter
    @AllArgsConstructor
    public enum CreatorSortCondition {
        LATEST, FOLLOW
    }
//        LATEST(Sort.by(Sort.Direction.DESC, "id"));
//
//        private final Sort sort;
//
//        public static Sort getMatchedSortCondition(String sort) {
//            if(Objects.isNull(sort) || sort.isBlank()) {
//                return LATEST.getSort();
//            }
//            return CreatorSortCondition.valueOf(sort.toUpperCase()).getSort();
//        }
//    }
}
