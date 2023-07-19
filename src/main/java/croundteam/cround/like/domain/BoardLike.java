package croundteam.cround.like.domain;

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
@Table(name = "board_like",
        uniqueConstraints = @UniqueConstraint(
                name = "board_like_board_and_member_composite_unique",
                columnNames = {"board_id", "member_id"}))
@EqualsAndHashCode(of = {"board", "member"})
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "fk_board_like_to_board"))
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_board_like_to_member"))
    private Member member;

    public BoardLike(Board board, Member member) {
        this.board = board;
        this.member = member;
    }

}
