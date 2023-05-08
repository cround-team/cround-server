package croundteam.cround.tag.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    public static Tags toTagsByNames(String... names) {
        if(Objects.isNull(names)) {
            /**
             * TODO: 태그가 0개여도 가능한지 또는 무조건 1개 이상이어야 하는지에 대한 것도 구체화하기
             * throws new NotEmptyTagException()
             */
        }
        List<Tag> collect = Arrays.stream(names)
                .map(name -> Tag.of(name))
                .collect(Collectors.toList());
        return new Tags(collect);
    }
}
