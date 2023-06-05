package croundteam.cround.bookmark.domain;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.exception.InvalidBookmarkException;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardBookmarks {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BoardBookmark> boardBookmarks = new ArrayList<>();

    public void bookmark(Board board, Member member) {
        BoardBookmark bookmark = new BoardBookmark(board, member);
        validateBookmark(bookmark);
        boardBookmarks.add(bookmark);
    }

    private void validateBookmark(BoardBookmark bookmark) {
        if(boardBookmarks.contains(bookmark)) {
            throw new InvalidBookmarkException(ErrorCode.DUPLICATE_BOOKMARK);
        }
    }

    public void unbookmark(Board board, Member member) {
        BoardBookmark bookmark = new BoardBookmark(board, member);
        boardBookmarks.remove(bookmark);
    }

    public int getBookmarkCount() {
        return boardBookmarks.size();
    }

    public boolean isBookmarkedBy(Board board, Member member) {
        BoardBookmark bookmark = new BoardBookmark(board, member);
        return boardBookmarks.contains(bookmark);
    }
}
