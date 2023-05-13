package croundteam.cround.tag.domain;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.tag.ExceedTagLengthException;
import croundteam.cround.common.exception.tag.ExceedTagsSizeException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static croundteam.cround.common.fixtures.ConstantFixtures.CREATOR_TAGS_MAX_SIZE;
import static croundteam.cround.common.fixtures.ConstantFixtures.CREATOR_TAG_MAX_LENGTH;

public class Tags {

    private List<Tag> tags;

    private Tags(List<Tag> tags) {
        validateTags(tags);
        this.tags = tags;
    }

    private void validateTags(List<Tag> tags) {
        validateTagsSize(tags);
        validateTagLength(tags);
        this.tags = tags;
    }

    private void validateTagLength(List<Tag> tags) {
        for (Tag tag : tags) {
            if(tag.getTagName().length() > CREATOR_TAG_MAX_LENGTH) {
                throw new ExceedTagLengthException(ErrorCode.EXCEED_TAG_LENGTH);
            }
        }
    }

    private void validateTagsSize(List<Tag> tags) {
        if(tags.size() > CREATOR_TAGS_MAX_SIZE) {
            throw new ExceedTagsSizeException(ErrorCode.EXCEED_TAGS_MAX_SIZE);
        }
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
