package croundteam.cround.like.application.dto;

import croundteam.cround.board.domain.Board;
import croundteam.cround.member.domain.Member;
import croundteam.cround.shortform.domain.ShortForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponse {

    private int likesCount;
    private boolean isLiked;

    public LikeResponse(Board board, Member member) {
        this.likesCount = board.getBoardLikes();
        this.isLiked = board.isLikedBy(member);
    }

    public LikeResponse(ShortForm shortForm, Member member) {
        this.likesCount = shortForm.getShortFormLikes();
        this.isLiked = shortForm.isLikedBy(member);
    }
}
