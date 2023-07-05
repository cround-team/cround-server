package croundteam.cround.board.domain;

import croundteam.cround.board.exception.InvalidLikeException;
import croundteam.cround.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("콘텐츠 좋아요는")
class BoardLikesTest {

    @Test
    @DisplayName("사용자가 좋아요 할 수 있다.")
    void bookmarkBoard() {
//        Board board = Board.builder().title(Title.create("제목")).build();
//        Member member = Member.builder().nickname("크라운더").build();
//
//        BoardLikes boardLikes = new BoardLikes();
//        boardLikes.like(board, member);
//
//        assertThat(boardLikes.getLikeCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("동일한 사용자가 두 번 이상할 경우 예외를 반환한다.")
    void doubleBookmarkBoard_fail() {
//        Board board = Board.builder().title(Title.create("제목")).build();
//        Member member = Member.builder().nickname("크라운더").build();
//
//        BoardLikes boardLikes = new BoardLikes();
//        boardLikes.like(board, member);
//
//        assertThatThrownBy(() -> boardLikes.like(board, member))
//                .isInstanceOf(InvalidLikeException.class)
//                .hasMessage("이미 좋아요한 대상입니다.");
    }
}