package croundteam.cround.review.domain;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.Member;
import croundteam.cround.review.exception.ExceedRatingRangeException;
import croundteam.cround.review.exception.InvalidContentLengthException;
import croundteam.cround.review.service.dto.ReviewSaveRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review {

    private static final int MINIMUM_RATING = 1;
    private static final int MAXIMUM_RATING = 5;
    private static final int MAXIMUM_CONTENT_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "content", nullable = false, length = MAXIMUM_CONTENT_LENGTH)
    private String content;

    @Column(nullable = false)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Review(String content, int rating, Creator creator, Member member) {
        validateRatingRange(rating);
        validateContentSize(content);

        this.content = content;
        this.rating = rating;
        this.creator = creator;
        this.member = member;
    }

    public static Review create(ReviewSaveRequest reviewSaveRequest, Creator creator, Member member) {
        return Review.builder()
                .content(reviewSaveRequest.getContent())
                .rating(reviewSaveRequest.getRating())
                .creator(creator)
                .member(member)
                .build();
    }

    private void validateContentSize(String content) {
        if (content.isBlank() || content.length() > MAXIMUM_CONTENT_LENGTH) {
            throw new InvalidContentLengthException(ErrorCode.INVALID_CONTENT_LENGTH);
        }
    }

    private void validateRatingRange(int rating) {
        if (rating > MAXIMUM_RATING && rating < MINIMUM_RATING) {
            throw new ExceedRatingRangeException(ErrorCode.EXCEED_RATING_RANGE);
        }
    }
}
