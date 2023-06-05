package croundteam.cround.bookmark.domain;

import croundteam.cround.board.domain.Board;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_bookmark")
@EqualsAndHashCode(of = {"board", "member"})
public class BoardBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "fk_board_bookmark_to_board"))
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_board_bookmark_to_member"))
    private Member member;

    public BoardBookmark(Board board, Member member) {
        this.board = board;
        this.member = member;
    }
}
