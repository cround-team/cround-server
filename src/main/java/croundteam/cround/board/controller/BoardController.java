package croundteam.cround.board.controller;

import croundteam.cround.board.dto.BoardSaveRequest;
import croundteam.cround.board.dto.BoardsResponse;
import croundteam.cround.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Void> saveBoard(
            @PathVariable Long memberId,
            @RequestBody @Valid BoardSaveRequest boardSaveRequest
    ) {
        Long boardId = boardService.saveBoard(memberId, boardSaveRequest);
        return ResponseEntity.created(URI.create("/api/boards/" + boardId)).build();
    }

    @GetMapping
    public ResponseEntity<BoardsResponse> findBoards() {
        BoardsResponse boards = boardService.findBoards();
        return ResponseEntity.ok(boards);
    }

}
