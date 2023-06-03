package croundteam.cround.board.controller;

import croundteam.cround.board.service.BoardService;
import croundteam.cround.board.service.dto.BoardSaveRequest;
import croundteam.cround.board.service.dto.BoardsResponse;
import croundteam.cround.board.service.dto.BookmarkResponse;
import croundteam.cround.board.service.dto.LikeResponse;
import croundteam.cround.member.service.dto.LoginMember;
import croundteam.cround.security.support.Login;
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

    @PostMapping
    public ResponseEntity<Void> saveBoard(
            @Login LoginMember member,
            @RequestBody @Valid BoardSaveRequest boardSaveRequest
    ) {
        Long boardId = boardService.saveBoard(member, boardSaveRequest);
        return ResponseEntity.created(URI.create("/api/boards/" + boardId)).build();
    }

    @GetMapping
    public ResponseEntity<BoardsResponse> findBoards() {
        BoardsResponse boards = boardService.findBoards();
        return ResponseEntity.ok(boards);
    }

    @PostMapping("/{boardId}/bookmarks")
    public ResponseEntity<BookmarkResponse> bookmarkBoard(
            @Login LoginMember loginMember,
            @PathVariable Long boardId
    ) {
        BookmarkResponse bookmarkResponse = boardService.bookmarkBoard(loginMember, boardId);
        return ResponseEntity.ok(bookmarkResponse);
    }

    @DeleteMapping("/{boardId}/bookmarks")
    public ResponseEntity<BookmarkResponse> unbookmarkBoard(
            @Login LoginMember loginMember,
            @PathVariable Long boardId
    ) {
        BookmarkResponse bookmarkResponse = boardService.unbookmarkBoard(loginMember, boardId);
        return ResponseEntity.ok(bookmarkResponse);
    }

    @PostMapping("/{boardId}/likes")
    public ResponseEntity<LikeResponse> likeBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {
        LikeResponse likeResponse = boardService.likeBoard(loginMember, boardId);
        return ResponseEntity.ok(likeResponse);
    }

    @DeleteMapping("/{boardId}/likes")
    public ResponseEntity<LikeResponse> unlikeBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {
        LikeResponse likeResponse = boardService.unlikeBoard(loginMember, boardId);
        return ResponseEntity.ok(likeResponse);
    }
}
