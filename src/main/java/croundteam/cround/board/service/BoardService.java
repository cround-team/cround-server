package croundteam.cround.board.service;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.repository.BoardRepository;
import croundteam.cround.board.service.dto.*;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.board.exception.NotExistBoardException;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.service.dto.LoginMember;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final CreatorRepository creatorRepository;
    private final MemberRepository memberRepository;

    public BoardsResponse findBoards() {
        List<BoardResponse> boardResponses = boardRepository.findAll()
                .stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
        return new BoardsResponse(boardResponses);
    }

    @Transactional
    public Long saveBoard(LoginMember loginMember, BoardSaveRequest boardSaveRequest) {
        Creator creator = findCreatorByEmail(loginMember.getEmail());
        Board board = Board.of(creator, boardSaveRequest);
        creator.addBoard(board);

        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

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

    private Creator findCreatorByEmail(String email) {
        return creatorRepository.findCreatorByEmail(email).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }
}
