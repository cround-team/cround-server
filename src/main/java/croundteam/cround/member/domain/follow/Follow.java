package croundteam.cround.member.domain.follow;

import croundteam.cround.common.domain.BaseTimeEntity;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.InvalidSourceTargetFollowException;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
        name = "follow_source_and_target_composite_unique",
        columnNames= {"source_id", "target_id"}))
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id", foreignKey = @ForeignKey(name = "fk_follow_to_source"))
    private Member source;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "target_id", foreignKey = @ForeignKey(name = "fk_follow_to_target"))
    private Creator target;

    private Follow(Member source, Creator target) {
        validateSourceAndTarget(source, target);
        this.source = source;
        this.target = target;
    }

    public static Follow of(Member source, Creator target) {
        return new Follow(source, target);
    }

    private void validateSourceAndTarget(Member source, Creator target) {
        Member targetMember = target.getMember();
        if(source.equals(targetMember)) {
            throw new InvalidSourceTargetFollowException(ErrorCode.INVALID_SOURCE_TARGET_FOLLOW);
        }
    }
}