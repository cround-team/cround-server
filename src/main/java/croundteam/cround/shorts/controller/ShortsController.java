package croundteam.cround.shorts.controller;

import croundteam.cround.board.dto.BookmarkResponse;
import croundteam.cround.board.dto.LikeResponse;
import croundteam.cround.member.dto.LoginMember;
import croundteam.cround.security.token.support.Login;
import croundteam.cround.shorts.dto.ShortsSaveRequest;
import croundteam.cround.shorts.service.ShortsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shorts")
public class ShortsController {

    private final ShortsService shortsService;

    @PostMapping
    public ResponseEntity<Void> saveShorts(
            @Login LoginMember member,
            @RequestBody @Valid ShortsSaveRequest shortsSaveRequest) {
        Long shortsId = shortsService.shortsSaveRequest(member, shortsSaveRequest);

        return ResponseEntity.created(URI.create("/api/shorts/" + shortsId)).build();
    }

    @PostMapping("/{shortsId}/bookmarks")
    public ResponseEntity<BookmarkResponse> bookmarkShorts(
            @Login LoginMember loginMember,
            @PathVariable Long shortsId
    ) {
        BookmarkResponse bookmarkResponse = shortsService.bookmarkShorts(loginMember, shortsId);
        return ResponseEntity.ok(bookmarkResponse);
    }

//    @DeleteMapping("/{shortsId}/bookmarks")
//    public ResponseEntity<BookmarkResponse> unbookmarkShorts(
//            @Login LoginMember loginMember,
//            @PathVariable Long shortsId
//    ) {
//        BookmarkResponse bookmarkResponse = shortsService.unbookmarkShorts(loginMember, shortsId);
//        return ResponseEntity.ok(bookmarkResponse);
//    }
//
//    @PostMapping("/{shortsId}/likes")
//    public ResponseEntity<LikeResponse> likeShorts(@Login LoginMember loginMember, @PathVariable Long shortsId) {
//        LikeResponse likeResponse = shortsService.likeShorts(loginMember, shortsId);
//        return ResponseEntity.ok(likeResponse);
//    }
//
//    @DeleteMapping("/{shortsId}/likes")
//    public ResponseEntity<LikeResponse> unlikeShorts(@Login LoginMember loginMember, @PathVariable Long shortsId) {
//        LikeResponse likeResponse = shortsService.unlikeShorts(loginMember, shortsId);
//        return ResponseEntity.ok(likeResponse);
//    }

}
