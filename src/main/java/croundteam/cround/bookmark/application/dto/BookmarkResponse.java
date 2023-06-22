package croundteam.cround.bookmark.application.dto;

import croundteam.cround.board.domain.Board;
import croundteam.cround.member.domain.Member;
import croundteam.cround.shortform.domain.ShortForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkResponse {

    int bookmarksCount;
    private boolean isBookmarked;

    public BookmarkResponse(Board board, Member member) {
        this.bookmarksCount = board.getBoardBookmarks();
        this.isBookmarked = board.isBookmarkedBy(member);
    }

    public BookmarkResponse(ShortForm shortForm, Member member) {
        this.bookmarksCount = shortForm.getShortFormBookmarks();
        this.isBookmarked = shortForm.isBookmarkedBy(member);
    }
}
