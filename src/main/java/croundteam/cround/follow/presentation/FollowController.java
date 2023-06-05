package croundteam.cround.follow.presentation;

import croundteam.cround.follow.application.FollowService;
import croundteam.cround.follow.application.dto.FollowRequest;
import croundteam.cround.follow.application.dto.FollowResponse;
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
    public ResponseEntity<FollowResponse> followCreator(@RequestBody FollowRequest followRequest) {
        FollowResponse followResponse = followService.followCreator(followRequest);
        return ResponseEntity.ok(followResponse);
    }

    @DeleteMapping("/following")
    public ResponseEntity<FollowResponse> unfollowCreator(@RequestBody FollowRequest followRequest) {
        FollowResponse followResponse = followService.unfollowCreator(followRequest);
        return ResponseEntity.ok(followResponse);
    }
}
