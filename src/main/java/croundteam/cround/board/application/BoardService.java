package croundteam.cround.board.application;

import croundteam.cround.board.application.dto.BoardSaveRequest;
import croundteam.cround.board.application.dto.FindBoardResponse;
import croundteam.cround.board.application.dto.SearchBoardsResponses;
import croundteam.cround.board.domain.Board;
import croundteam.cround.board.domain.BoardQueryRepository;
import croundteam.cround.board.domain.BoardRepository;
import croundteam.cround.board.exception.NotExistBoardException;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.CreatorRepository;
import croundteam.cround.creator.exception.InvalidCreatorException;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.support.search.SearchCondition;
import croundteam.cround.support.vo.AppUser;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final CreatorRepository creatorRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveBoard(LoginMember loginMember, BoardSaveRequest boardSaveRequest) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Creator creator = findCreatorByMember(member);

        Board board = Board.of(creator, boardSaveRequest);
        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

    public SearchBoardsResponses searchBoards(SearchCondition searchCondition, Pageable pageable, AppUser appUser) {
        Member member = getLoginMember(appUser);
        Slice<Board> boards = boardQueryRepository.searchByCondition(searchCondition, pageable);

        return new SearchBoardsResponses(boards, member);
    }

    public FindBoardResponse findOne(Long boardId, AppUser appUser) {
        Board board = findBoardById(boardId);
        Member member = getLoginMember(appUser);
        Creator creator = findCreatorByMember(member);

        return FindBoardResponse.from(board, member, creator);
    }

    @Transactional
    public void deleteBoard(Long boardId, LoginMember loginMember) {
        Board board = findBoardById(boardId);
        Member member = findMemberByEmail(loginMember.getEmail());
        Creator creator = findCreatorByMember(member);

        validateSameCreator(creator, board);

        boardRepository.deleteById(boardId);;
    }

    private static void validateSameCreator(Creator creator, Board board) {
        if(!board.isAuthoredBy(creator)) {
            throw new InvalidCreatorException(ErrorCode.INVALID_AUTHORIZATION);
        }
    }

    private Member getLoginMember(AppUser appUser) {
        if (appUser.isGuest()) {
            return null;
        }
        return findMemberByEmail(appUser.getEmail());
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findBoardById(boardId).orElseThrow(() -> {
            throw new NotExistBoardException(ErrorCode.NOT_EXIST_BOARD);
        });
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private Creator findCreatorByMember(Member member) {
        return creatorRepository.findCreatorByMember(member).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }
}
