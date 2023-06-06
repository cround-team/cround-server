package croundteam.cround.follow.presentation;

import croundteam.cround.follow.application.FollowService;
import croundteam.cround.follow.application.dto.FollowRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/following")
    public ResponseEntity<Void> followCreator(@RequestBody FollowRequest followRequest) {
        followService.followCreator(followRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/following")
    public ResponseEntity<Void> unfollowCreator(@RequestBody FollowRequest followRequest) {
        followService.unfollowCreator(followRequest);
        return ResponseEntity.ok().build();
    }
}
