package croundteam.cround.follow.presentation;

import croundteam.cround.follow.application.FollowService;
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
    public ResponseEntity<Void> followCreator(@PathVariable Long creatorId, @Login LoginMember loginMember) {
        followService.followCreator(creatorId, loginMember);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{creatorId}/following")
    public ResponseEntity<Void> unfollowCreator(@PathVariable Long creatorId, @Login LoginMember loginMember) {
        followService.unfollowCreator(creatorId, loginMember);
        return ResponseEntity.ok().build();
    }
}
