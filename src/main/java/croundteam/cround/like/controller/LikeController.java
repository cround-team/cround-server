package croundteam.cround.like.controller;

import croundteam.cround.like.dto.LikeResponse;
import croundteam.cround.like.service.LikeService;
import croundteam.cround.member.dto.LoginMember;
import croundteam.cround.security.token.support.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/boards/{boardId}")
    public ResponseEntity<LikeResponse> likeBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {
        LikeResponse likeResponse = likeService.likeBoard(loginMember, boardId);
        return ResponseEntity.ok(likeResponse);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<LikeResponse> unlikeBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {
        LikeResponse likeResponse = likeService.unlikeBoard(loginMember, boardId);
        return ResponseEntity.ok(likeResponse);
    }
}
