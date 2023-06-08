package croundteam.cround.creator.domain.tag;

import croundteam.cround.creator.domain.Creator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
        name = "creator_tag_creator_and_tag_composite_unique",
        columnNames= {"creator_id", "tag_id"}))
public class CreatorTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creator_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Tag tag;

    private CreatorTag(Creator creator, Tag tag) {
        this.creator = creator;
        this.tag = tag;
    }

    public static CreatorTag of(Creator creator, Tag tag) {
        return new CreatorTag(creator, tag);
    }

    public String getTagName() {
        return tag.getTagName();
    }
}
