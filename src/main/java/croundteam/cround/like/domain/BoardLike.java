package croundteam.cround.like.domain;

import croundteam.cround.board.domain.Board;
import croundteam.cround.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_like")
@EqualsAndHashCode(of = {"board", "member"})
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "fk_boardlike_to_board"))
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_boardlike_to_member"))
    private Member member;

    public BoardLike(Board board, Member member) {
        this.board = board;
        this.member = member;
    }

}
