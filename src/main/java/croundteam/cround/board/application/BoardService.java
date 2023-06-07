package croundteam.cround.board.application;

import croundteam.cround.board.application.dto.BoardSaveRequest;
import croundteam.cround.board.application.dto.FindBoardResponse;
import croundteam.cround.board.application.dto.SearchBoardsResponses;
import croundteam.cround.board.domain.Board;
import croundteam.cround.board.exception.NotExistBoardException;
import croundteam.cround.board.infrastructure.BoardQueryRepository;
import croundteam.cround.board.infrastructure.BoardRepository;
import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.creator.infrastructure.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.infrastructure.MemberRepository;
import croundteam.cround.security.support.AppUser;
import croundteam.cround.security.support.LoginMember;
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
        Creator creator = findCreatorByEmail(loginMember.getEmail());
        Board board = Board.of(creator, boardSaveRequest);

        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

    public SearchBoardsResponses searchBoards(SearchCondition searchCondition, Pageable pageable, AppUser appUser) {
        Member member = getLoginMember(appUser);
        Slice<Board> boards = boardQueryRepository.searchByCondition(searchCondition, pageable);

        return new SearchBoardsResponses(boards, member);
    }

    public FindBoardResponse findOne(Long boardId) {
        Board board = findBoardWithJoinById(boardId);
        return FindBoardResponse.from(board);
    }

    private Board findBoardWithJoinById(Long boardId) {
        return boardRepository.findBoardById(boardId).orElseThrow(() -> {
            throw new NotExistBoardException(ErrorCode.NOT_EXIST_BOARD);
        });
    }

    private Member getLoginMember(AppUser appUser) {
        if (appUser.isGuest()) {
            return null;
        }
        return findMemberByEmail(appUser.getEmail());
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private Creator findCreatorByEmail(String email) {
        return creatorRepository.findCreatorByEmail(email).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }
}
