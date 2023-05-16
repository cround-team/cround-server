package croundteam.cround.tag.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import croundteam.cround.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TagName tagName;

    private Tag(TagName tagName) {
        this.tagName = tagName;
    }

    @JsonCreator
    public static Tag from(String name) {
        return new Tag(TagName.from(name));
    }

    public String getTagName() {
        return tagName.getName();
    }
}
