package croundteam.cround.board.presentation;

import croundteam.cround.board.application.BoardService;
import croundteam.cround.board.application.dto.BoardSaveRequest;
import croundteam.cround.board.application.dto.FindBoardResponse;
import croundteam.cround.board.application.dto.SearchBoardsResponses;
import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.security.support.AppUser;
import croundteam.cround.security.support.Authenticated;
import croundteam.cround.security.support.Login;
import croundteam.cround.security.support.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    @PostMapping
    public ResponseEntity<Void> saveBoard(
            @Login LoginMember member,
            @RequestBody @Valid BoardSaveRequest boardSaveRequest
    ) {
        Long boardId = boardService.saveBoard(member, boardSaveRequest);
        return ResponseEntity.created(URI.create("/api/boards/" + boardId)).build();
    }

    @GetMapping
    public ResponseEntity<SearchBoardsResponses> searchBoards(
            SearchCondition searchCondition,
            Pageable pageable,
            @Authenticated AppUser appUser
    ) {
        SearchBoardsResponses searchBoardsResponses = boardService.searchBoards(searchCondition, pageable, appUser);
        return ResponseEntity.ok(searchBoardsResponses);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<FindBoardResponse> findOne(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.findOne(boardId));
    }
}
