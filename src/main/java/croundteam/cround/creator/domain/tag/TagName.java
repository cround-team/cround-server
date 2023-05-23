package croundteam.cround.creator.domain.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TagName {

    @Column(name = "tag_name", nullable = false)
    private String name;

    public TagName(String name) {
        this.name = name;
    }

    @JsonCreator
    public static TagName from(String tagName) {
        return new TagName(tagName);
    }
}
