package croundteam.cround.follow.domain;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
        name = "follow_source_and_target_composite_unique",
        columnNames= {"source_id", "target_id"}))
@EqualsAndHashCode(of = {"source", "target"})
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Member source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Creator target;

    private Follow(Member source, Creator target) {
        this.source = source;
        this.target = target;
    }

    public static Follow of(Member source, Creator target) {
        return new Follow(source, target);
    }
}
