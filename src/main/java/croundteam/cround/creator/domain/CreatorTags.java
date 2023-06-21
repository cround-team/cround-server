package croundteam.cround.creator.domain;

import croundteam.cround.creator.domain.tag.CreatorTag;
import croundteam.cround.creator.domain.tag.Tags;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Embeddable
@NoArgsConstructor
public class CreatorTags {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.ALL)
    private List<CreatorTag> creatorTags = new ArrayList<>();

    public CreatorTags(List<CreatorTag> creatorTags) {
        this.creatorTags = creatorTags;
    }

    public static CreatorTags create(Creator creator, Tags tags) {
        return new CreatorTags(tags.castCreatorTagsFromTags(creator));
    }

    public static CreatorTags create(List<CreatorTag> creatorTags) {
        return new CreatorTags(creatorTags);
    }

    public List<String> castTagsFromCreatorTags() {
        return creatorTags
                .stream()
                .map(creatorTag -> creatorTag.getTagName())
                .collect(Collectors.toList());
    }
}
