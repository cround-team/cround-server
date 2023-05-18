package croundteam.cround.like.service;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.repository.BoardRepository;
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
public class LikeService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public LikeResponse likeBoard(LoginMember loginMember, Long boardId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Board board = findBoardById(boardId);

        board.like(member);

        return new LikeResponse(board.getBoardLikes());
    }

    @Transactional
    public LikeResponse unlikeBoard(LoginMember loginMember, Long boardId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Board board = findBoardById(boardId);

        board.unlike(member);

        return new LikeResponse(board.getBoardLikes());
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
