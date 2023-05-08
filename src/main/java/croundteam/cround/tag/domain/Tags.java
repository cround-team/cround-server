package croundteam.cround.tag.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Tags {

    private List<Tag> tags;

    private Tags(List<Tag> tags) {
        validateTags(tags);
        this.tags = tags;
    }

    private void validateTags(List<Tag> tags) {
        /**
         * 태그의 개수 제한
         * 또는 각 태그의 글자 제한을 검증
         */
    }

    public static Tags from(List<Tag> tags) {
        return new Tags(tags);
    }


    public List<Tag> toList() {
        return Collections.unmodifiableList(tags);
    }

    public static List<Tag> toListByNames(String... names) {
        return Arrays.stream(names)
                .map(name -> Tag.of(name))
                .collect(Collectors.toList());
    }
}
