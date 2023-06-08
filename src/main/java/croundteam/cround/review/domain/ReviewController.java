package croundteam.cround.review.domain;

import croundteam.cround.review.service.dto.ReviewSaveRequest;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class ReviewController {

    @PostMapping("/{creatorId}/reviews")
    public void saveReview(@PathVariable Long creatorId,
                           @Login LoginMember loginMember,
                           @RequestBody @Valid ReviewSaveRequest reviewSaveRequest
    ) {



    }
}
