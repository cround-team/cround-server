package croundteam.cround.creator.domain.tag;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.CreatorTag;
import croundteam.cround.creator.exception.ExceedTagLengthException;
import croundteam.cround.creator.exception.ExceedTagsSizeException;
import croundteam.cround.creator.exception.NotEmptyTagException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static croundteam.cround.common.fixtures.ConstantFixtures.*;

public class Tags {

    private List<Tag> tags;

    private Tags(List<Tag> tags) {
        validateTagEmpty(tags);
        validateTagsSize(tags);
        validateTagLength(tags);
        this.tags = tags;
    }

    public static Tags create(List<Tag> tags) {
        return new Tags(tags);
    }

    public static Tags create(String... names) {
        List<Tag> tags = Arrays.stream(names).map(Tag::from).collect(Collectors.toList());
        return new Tags(tags);
    }

    public List<CreatorTag> castCreatorTagsFromTags(Creator creator) {
        return tags.stream()
                .map(tag -> CreatorTag.of(creator, tag))
                .collect(Collectors.toList());
    }

    private void validateTagEmpty(List<Tag> tags) {
        if(Objects.isNull(tags) || tags.isEmpty()) {
            throw new NotEmptyTagException(ErrorCode.NOT_EMPTY_TAG);
        }
    }

    private void validateTagLength(List<Tag> tags) {
        for (Tag tag : tags) {
            if(tag.getTagName().length() > CREATOR_TAG_MAX_LENGTH) {
                throw new ExceedTagLengthException(ErrorCode.EXCEED_TAG_LENGTH);
            }
        }
    }

    private void validateTagsSize(List<Tag> tags) {
        if(tags.size() > CREATOR_TAGS_MAX_SIZE || tags.size() < CREATOR_TAGS_MIN_SIZE) {
            throw new ExceedTagsSizeException(ErrorCode.EXCEED_TAGS_SIZE);
        }
    }
}
