package croundteam.cround.shorts.domain;

import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shorts_like")
@EqualsAndHashCode(of = {"shorts", "member"})
public class ShortsLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shorts_id", foreignKey = @ForeignKey(name = "fk_shorts_like_to_board"))
    private Shorts shorts;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_shorts_like_to_member"))
    private Member member;

    private ShortsLike(Shorts shorts, Member member) {
        this.shorts = shorts;
        this.member = member;
    }

    public static ShortsLike of(Shorts shorts, Member member) {
        return new ShortsLike(shorts, member);
    }
}
