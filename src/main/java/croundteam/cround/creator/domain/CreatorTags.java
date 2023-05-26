package croundteam.cround.creator.domain;

import croundteam.cround.creator.domain.tag.Tags;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor
public class CreatorTags {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<CreatorTag> creatorTags = new ArrayList<>();

    public CreatorTags(List<CreatorTag> creatorTags) {
        this.creatorTags = creatorTags;
    }

    public static CreatorTags create(Creator creator, Tags tags) {
        return new CreatorTags(tags.castCreatorTagsFromTags(creator));
    }
}
