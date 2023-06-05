package croundteam.cround.board.domain;

import croundteam.cround.board.exception.InvalidBookmarkException;
import croundteam.cround.bookmark.domain.BoardBookmarks;
import croundteam.cround.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("콘텐츠 북마크는")
class BoardBookmarksTest {

    @Test
    @DisplayName("사용자가 북마크 할 수 있다.")
    void bookmarkBoard() {
        Board board = Board.builder().title(Title.create("제목")).build();
        Member member = Member.builder().nickname("크라운더").build();

        BoardBookmarks bookmarks = new BoardBookmarks();
        bookmarks.bookmark(board, member);

        assertThat(bookmarks.getBookmarkCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("동일한 사용자가 두 번 이상할 경우 예외를 반환한다.")
    void doubleBookmarkBoard_fail() {
        Board board = Board.builder().title(Title.create("제목")).build();
        Member member = Member.builder().nickname("크라운더").build();

        BoardBookmarks bookmarks = new BoardBookmarks();
        bookmarks.bookmark(board, member);

        assertThatThrownBy(() -> bookmarks.bookmark(board, member))
                .isInstanceOf(InvalidBookmarkException.class)
                .hasMessage("이미 북마크한 대상입니다.");
    }
}