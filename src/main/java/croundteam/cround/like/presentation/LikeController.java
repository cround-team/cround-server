package croundteam.cround.like.presentation;

import croundteam.cround.like.application.LikeService;
import croundteam.cround.like.application.dto.LikeResponse;
import croundteam.cround.security.support.Login;
import croundteam.cround.security.support.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/boards/{boardId}/likes")
    public ResponseEntity<LikeResponse> likeBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {
        LikeResponse likeResponse = likeService.likeBoard(loginMember, boardId);
        return ResponseEntity.ok(likeResponse);
    }

    @DeleteMapping("/boards/{boardId}/likes")
    public ResponseEntity<LikeResponse> unlikeBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {
        LikeResponse likeResponse = likeService.unlikeBoard(loginMember, boardId);
        return ResponseEntity.ok(likeResponse);
    }

    @PostMapping("/shorts/{shortsId}/likes")
    public ResponseEntity<LikeResponse> likeShortForm(@Login LoginMember loginMember, @PathVariable Long shortsId) {
        LikeResponse likeResponse = likeService.likeShortForm(loginMember, shortsId);
        return ResponseEntity.ok(likeResponse);
    }

    @DeleteMapping("/shorts/{shortsId}/likes")
    public ResponseEntity<LikeResponse> unlikeShorts(@Login LoginMember loginMember, @PathVariable Long shortsId) {
        LikeResponse likeResponse = likeService.unlikeShortForm(loginMember, shortsId);
        return ResponseEntity.ok(likeResponse);
    }
}
