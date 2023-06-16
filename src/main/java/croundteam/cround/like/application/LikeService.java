package croundteam.cround.like.application;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.domain.BoardRepository;
import croundteam.cround.board.exception.NotExistBoardException;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.like.application.dto.LikeResponse;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.domain.ShortFormRepository;
import croundteam.cround.shortform.exception.NotExistShortFormException;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ShortFormRepository shortFormRepository;

    @Transactional
    public LikeResponse likeBoard(LoginMember loginMember, Long boardId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Board board = findBoardById(boardId);

        board.like(member);

        return new LikeResponse(board, member);
    }

    @Transactional
    public LikeResponse unlikeBoard(LoginMember loginMember, Long boardId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Board board = findBoardById(boardId);

        board.unlike(member);

        return new LikeResponse(board, member);
    }

    @Transactional
    public LikeResponse likeShortForm(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        ShortForm shortForm = findShortFormById(shortsId);

        shortForm.like(member);

        return new LikeResponse(shortForm, member);
    }

    @Transactional
    public LikeResponse unlikeShortForm(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        ShortForm shortForm = findShortFormById(shortsId);

        shortForm.unlike(member);

        return new LikeResponse(shortForm, member);
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
