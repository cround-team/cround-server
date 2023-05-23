package croundteam.cround.shorts.domain.bookmark;

import croundteam.cround.board.domain.Board;
import croundteam.cround.member.domain.Member;
import croundteam.cround.shorts.domain.Shorts;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shorts_bookmark")
@EqualsAndHashCode(of = {"shorts", "member"})
public class ShortsBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shorts_id", foreignKey = @ForeignKey(name = "fk_shorts_bookmark_to_shorts"))
    private Shorts shorts;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_shorts_bookmark_to_member"))
    private Member member;
}
