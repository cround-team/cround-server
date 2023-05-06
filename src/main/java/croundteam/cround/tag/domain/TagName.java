package croundteam.cround.tag.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TagName {

    // IN 절로 데이터 가져오기
    @Column(name = "tag_name", nullable = false)
    private String name;

    public TagName(String name) {
        this.name = name;
    }

    public static TagName from(String tagName) {
        return new TagName(tagName);
    }
}
