package croundteam.cround.review.presentation;

import croundteam.cround.review.application.ReviewService;
import croundteam.cround.review.application.dto.ReviewSaveRequest;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{creatorId}/reviews")
    public ResponseEntity<Void> saveReview(@PathVariable Long creatorId,
                                           @Login LoginMember loginMember,
                                           @RequestBody @Valid ReviewSaveRequest reviewSaveRequest
    ) {
        Long reviewId = reviewService.saveReview(creatorId, loginMember, reviewSaveRequest);
        return ResponseEntity.created(URI.create("/api/creators/" + creatorId + "/reviews/" + reviewId)).build();
    }
}
