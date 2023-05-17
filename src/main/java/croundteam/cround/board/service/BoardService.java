package croundteam.cround.board.service;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.dto.BoardResponse;
import croundteam.cround.board.dto.BoardSaveRequest;
import croundteam.cround.board.dto.BoardsResponse;
import croundteam.cround.board.repository.BoardRepository;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.NotExistCreatorException;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.repository.CreatorRepository;
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

    @Transactional
    public Long saveBoard(Long memberId, BoardSaveRequest boardSaveRequest) {
        Creator creator = findCreatorById(memberId);
        Board board = Board.of(creator, boardSaveRequest);
        creator.addBoard(board);

        Board saveBoard = boardRepository.save(board);

        return saveBoard.getId();
    }

    public BoardsResponse findBoards() {
        List<BoardResponse> boardResponses = boardRepository.findAll()
                .stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
        return new BoardsResponse(boardResponses);
    }

    private Creator findCreatorById(Long memberId) {
        return creatorRepository.findCreatorByMemberId(memberId).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }
}
