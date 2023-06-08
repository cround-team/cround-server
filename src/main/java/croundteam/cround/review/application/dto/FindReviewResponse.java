package croundteam.cround.review.application.dto;

import croundteam.cround.review.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FindReviewResponse {

    private Long memberId;
    private String nickname;
    private int rating;
    private String content;
    private LocalDateTime createdAt;

    public FindReviewResponse(Review review) {
        this.memberId = review.getMemberId();
        this.nickname = review.getWriter();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}
