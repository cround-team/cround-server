package croundteam.cround.bookmark.service;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.repository.BoardRepository;
import croundteam.cround.bookmark.dto.BookmarkResponse;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.like.NotExistBoardException;
import croundteam.cround.common.exception.member.NotExistMemberException;
import croundteam.cround.like.dto.LikeResponse;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.dto.LoginMember;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

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

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NotExistBoardException(ErrorCode.NOT_EXIST_BOARD));
    }
}
