package croundteam.cround.follow.presentation;

import croundteam.cround.follow.application.FollowService;
import croundteam.cround.follow.application.dto.FollowResponse;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.vo.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/creators")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{creatorId}/following")
    public ResponseEntity<FollowResponse> followCreator(@PathVariable Long creatorId, @Login LoginMember loginMember) {
        return ResponseEntity.ok(followService.followCreator(creatorId, loginMember));
    }

    @DeleteMapping("/{creatorId}/following")
    public ResponseEntity<FollowResponse> unfollowCreator(@PathVariable Long creatorId, @Login LoginMember loginMember) {
        followService.unfollowCreator(creatorId, loginMember);
        return ResponseEntity.ok(followService.followCreator(creatorId, loginMember));
    }
}
