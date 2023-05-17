package croundteam.cround.creator.domain.tag;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatorTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creator_id", foreignKey = @ForeignKey(name = "fk_creatortag_to_creator"))
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_creatortag_to_tag"))
    private Tag tag;

    private CreatorTag(Creator creator, Tag tag) {
        this.creator = creator;
        this.tag = tag;
    }

    public static CreatorTag of(Creator creator, Tag tag) {
        return new CreatorTag(creator, tag);
    }
}
