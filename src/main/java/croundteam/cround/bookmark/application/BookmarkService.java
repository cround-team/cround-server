package croundteam.cround.bookmark.application;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.exception.NotExistBoardException;
import croundteam.cround.board.domain.BoardRepository;
import croundteam.cround.bookmark.application.dto.BookmarkResponse;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.support.vo.LoginMember;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.exception.NotExistShortFormException;
import croundteam.cround.shortform.domain.ShortFormRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ShortFormRepository shortFormRepository;

    @Transactional
    public BookmarkResponse bookmarkBoard(LoginMember loginMember, Long boardId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Board board = findBoardById(boardId);

        board.bookmark(member);

        return new BookmarkResponse(board.getBoardBookmarks());
    }

    @Transactional
    public BookmarkResponse unbookmarkBoard(LoginMember loginMember, Long boardId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Board board = findBoardById(boardId);

        board.unbookmark(member);

        return new BookmarkResponse(board.getBoardBookmarks());
    }

    @Transactional
    public BookmarkResponse bookmarkShortForm(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        ShortForm shortForm = findShortFormById(shortsId);

        shortForm.bookmark(member);

        return new BookmarkResponse(shortForm.getBookmarkCount());
    }

    @Transactional
    public BookmarkResponse unbookmarkShortForm(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        ShortForm shortForm = findShortFormById(shortsId);

        shortForm.unbookmark(member);

        return new BookmarkResponse(shortForm.getBookmarkCount());
    }

    private ShortForm findShortFormById(Long shortsId) {
        return shortFormRepository.findById(shortsId).orElseThrow(
                () -> new NotExistShortFormException(ErrorCode.NOT_EXIST_SHORT_FORM));
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NotExistBoardException(ErrorCode.NOT_EXIST_BOARD));
    }
}
