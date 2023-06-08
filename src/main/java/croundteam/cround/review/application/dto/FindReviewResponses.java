package croundteam.cround.review.application.dto;

import croundteam.cround.review.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
public class FindReviewResponses {

    private List<FindReviewResponse> reviews = new ArrayList<>();

    public FindReviewResponses(List<Review> reviews) {
        this.reviews = reviews.stream().map(FindReviewResponse::new).collect(toList());
    }
}
