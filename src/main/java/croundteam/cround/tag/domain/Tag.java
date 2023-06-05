package croundteam.cround.tag.domain;

import croundteam.cround.common.domain.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TagName tagName;

    private Tag(TagName tagName) {
        this.tagName = tagName;
    }

    public static Tag from(String name) {
        return new Tag(TagName.from(name));
    }

    public String getTagName() {
        return tagName.getName();
    }

}
