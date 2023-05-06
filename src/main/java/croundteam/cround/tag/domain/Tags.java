package croundteam.cround.tag.domain;

import java.util.Collections;
import java.util.List;

public class Tags {

    private List<Tag> tags;

    public Tags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> toList() {
        return Collections.unmodifiableList(tags);
    }
}
