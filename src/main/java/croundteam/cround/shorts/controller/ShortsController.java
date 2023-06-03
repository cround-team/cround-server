package croundteam.cround.shorts.controller;

import croundteam.cround.board.service.dto.BookmarkResponse;
import croundteam.cround.board.service.dto.LikeResponse;
import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.member.service.dto.LoginMember;
import croundteam.cround.security.support.AppUser;
import croundteam.cround.security.support.Authenticated;
import croundteam.cround.security.support.Login;
import croundteam.cround.shorts.service.ShortsService;
import croundteam.cround.shorts.service.dto.SearchShortsResponses;
import croundteam.cround.shorts.service.dto.ShortsSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ResponseEntity<SearchShortsResponses> searchShorts(
            SearchCondition searchCondition,
            Pageable pageable,
            @Authenticated AppUser appUser
    ) {
        SearchShortsResponses searchShortsResponses = shortsService.searchShorts(searchCondition, pageable, appUser);

        return ResponseEntity.ok(searchShortsResponses);
    }

    @PostMapping("/{shortsId}/bookmarks")
    public ResponseEntity<BookmarkResponse> bookmarkShorts(
            @Login LoginMember loginMember,
            @PathVariable Long shortsId
    ) {
        BookmarkResponse bookmarkResponse = shortsService.bookmarkShorts(loginMember, shortsId);
        return ResponseEntity.ok(bookmarkResponse);
    }

    @DeleteMapping("/{shortsId}/bookmarks")
    public ResponseEntity<BookmarkResponse> unbookmarkShorts(
            @Login LoginMember loginMember,
            @PathVariable Long shortsId
    ) {
        BookmarkResponse bookmarkResponse = shortsService.unbookmarkShorts(loginMember, shortsId);
        return ResponseEntity.ok(bookmarkResponse);
    }

    @PostMapping("/{shortsId}/likes")
    public ResponseEntity<LikeResponse> likeShorts(@Login LoginMember loginMember, @PathVariable Long shortsId) {
        LikeResponse likeResponse = shortsService.likeShorts(loginMember, shortsId);
        return ResponseEntity.ok(likeResponse);
    }

    @DeleteMapping("/{shortsId}/likes")
    public ResponseEntity<LikeResponse> unlikeShorts(@Login LoginMember loginMember, @PathVariable Long shortsId) {
        LikeResponse likeResponse = shortsService.unlikeShorts(loginMember, shortsId);
        return ResponseEntity.ok(likeResponse);
    }
}
