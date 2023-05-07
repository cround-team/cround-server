package croundteam.cround.member.domain.follow;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.InvalidSourceTargetFollowException;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
        name = "follow_source_and_target_composite_unique",
        columnNames= {"source_id", "target_id"}))
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", foreignKey = @ForeignKey(name = "fk_follow_to_source"))
    private Member source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", foreignKey = @ForeignKey(name = "fk_follow_to_target"))
    private Creator target;

    public Follow(Member source, Creator target) {
        validateSourceAndTarget(source, target);
        this.source = source;
        this.target = target;
    }

    private void validateSourceAndTarget(Member source, Creator target) {
        Member targetMember = target.getMember();

        if(source.equals(targetMember)) {
            throw new InvalidSourceTargetFollowException(ErrorCode.INVALID_SOURCE_TARGET_FOLLOW);
        }
    }
}
