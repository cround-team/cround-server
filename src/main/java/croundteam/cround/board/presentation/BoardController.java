package croundteam.cround.board.presentation;

import croundteam.cround.board.application.BoardService;
import croundteam.cround.board.application.dto.BoardSaveRequest;
import croundteam.cround.board.application.dto.BoardUpdateRequest;
import croundteam.cround.board.application.dto.FindBoardResponse;
import croundteam.cround.board.application.dto.SearchBoardsResponses;
import croundteam.cround.support.annotation.Authenticated;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.search.SearchCondition;
import croundteam.cround.support.vo.AppUser;
import croundteam.cround.support.vo.LoginMember;
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
            @Login LoginMember loginMember,
            @RequestBody @Valid BoardSaveRequest boardSaveRequest
    ) {
        Long boardId = boardService.saveBoard(loginMember, boardSaveRequest);
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
    public ResponseEntity<FindBoardResponse> findOne(@PathVariable Long boardId, @Authenticated AppUser appUser) {
        return ResponseEntity.ok(boardService.findOne(boardId, appUser));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long boardId,
            @Login LoginMember loginMember
    ) {
        boardService.deleteBoard(boardId, loginMember);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<Void> updateBoard(
            @PathVariable final Long boardId,
            @RequestBody final BoardUpdateRequest boardUpdateRequest,
            @Login final LoginMember loginMember
    ) {
        boardService.updateBoard(boardId, boardUpdateRequest, loginMember);
        return ResponseEntity.ok().build();
    }

}
