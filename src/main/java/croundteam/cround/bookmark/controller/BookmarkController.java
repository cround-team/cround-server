package croundteam.cround.bookmark.controller;

import croundteam.cround.bookmark.dto.BookmarkResponse;
import croundteam.cround.bookmark.service.BookmarkService;
import croundteam.cround.member.dto.LoginMember;
import croundteam.cround.security.token.support.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/boards/{boardId}")
    public ResponseEntity<BookmarkResponse> bookmarkBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {
        BookmarkResponse bookmarkResponse = bookmarkService.bookmarkBoard(loginMember, boardId);
        return ResponseEntity.ok(bookmarkResponse);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<BookmarkResponse> unbookmarkBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {
        BookmarkResponse bookmarkResponse = bookmarkService.unbookmarkBoard(loginMember, boardId);
        return ResponseEntity.ok(bookmarkResponse);
    }
}
