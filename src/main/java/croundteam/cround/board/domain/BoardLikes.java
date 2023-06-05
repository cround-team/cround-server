package croundteam.cround.board.domain;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.exception.InvalidLikeException;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.like.domain.BoardLike;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLikes {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BoardLike> boardLikes = new ArrayList<>();

    public void like(Board board, Member member) {
        BoardLike like = new BoardLike(board, member);
        validateLike(like);
        boardLikes.add(like);
    }

    private void validateLike(BoardLike like) {
        if(boardLikes.contains(like)) {
            throw new InvalidLikeException(ErrorCode.DUPLICATE_LIKE);
        }
    }

    public void unlike(Board board, Member member) {
        BoardLike like = new BoardLike(board, member);
        boardLikes.remove(like);
    }

    public int getLikeCount() {
        return boardLikes.size();
    }

    public boolean isLikedBy(Board board, Member member) {
        BoardLike like = new BoardLike(board, member);
        return boardLikes.contains(like);
    }
}
