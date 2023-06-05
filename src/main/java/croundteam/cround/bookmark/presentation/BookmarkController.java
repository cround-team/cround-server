package croundteam.cround.bookmark.presentation;

import croundteam.cround.bookmark.application.BookmarkService;
import croundteam.cround.bookmark.application.dto.BookmarkResponse;
import croundteam.cround.security.support.Login;
import croundteam.cround.security.support.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/boards/{boardId}/bookmarks")
    public ResponseEntity<BookmarkResponse> bookmarkBoard(
            @Login LoginMember loginMember,
            @PathVariable Long boardId
    ) {
        BookmarkResponse bookmarkResponse = bookmarkService.bookmarkBoard(loginMember, boardId);
        return ResponseEntity.ok(bookmarkResponse);
    }

    @DeleteMapping("/boards/{boardId}/bookmarks")
    public ResponseEntity<BookmarkResponse> unbookmarkBoard(
            @Login LoginMember loginMember,
            @PathVariable Long boardId
    ) {
        BookmarkResponse bookmarkResponse = bookmarkService.unbookmarkBoard(loginMember, boardId);
        return ResponseEntity.ok(bookmarkResponse);
    }

    @PostMapping("/shorts/{shortsId}/bookmarks")
    public ResponseEntity<BookmarkResponse> bookmarkShortFrom(
            @Login LoginMember loginMember,
            @PathVariable Long shortsId
    ) {
        BookmarkResponse bookmarkResponse = bookmarkService.bookmarkShortForm(loginMember, shortsId);
        return ResponseEntity.ok(bookmarkResponse);
    }

    @DeleteMapping("/shorts/{shortsId}/bookmarks")
    public ResponseEntity<BookmarkResponse> unbookmarkShortForm(
            @Login LoginMember loginMember,
            @PathVariable Long shortsId
    ) {
        BookmarkResponse bookmarkResponse = bookmarkService.unbookmarkShortForm(loginMember, shortsId);
        return ResponseEntity.ok(bookmarkResponse);
    }
}
