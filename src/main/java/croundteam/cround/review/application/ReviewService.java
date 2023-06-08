package croundteam.cround.review.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.CreatorRepository;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.review.application.dto.FindReviewResponses;
import croundteam.cround.review.application.dto.ReviewSaveRequest;
import croundteam.cround.review.domain.Review;
import croundteam.cround.review.domain.ReviewQueryRepository;
import croundteam.cround.review.domain.ReviewRepository;
import croundteam.cround.support.search.SimpleSearchCondition;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final CreatorRepository creatorRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveReview(Long creatorId, LoginMember loginMember, ReviewSaveRequest reviewSaveRequest) {
        Creator creator = findCreatorById(creatorId);
        Member member = findMemberByEmail(loginMember.getEmail());

        Review review = Review.create(reviewSaveRequest, creator, member);
        creator.addReview(review);

        Review saveReview = reviewRepository.save(review);
        return saveReview.getId();
    }

    public FindReviewResponses findReviews(Long creatorId, SimpleSearchCondition searchCondition) {
        Creator creator = findCreatorById(creatorId);
        List<Review> reviews = reviewQueryRepository.findReviewsByCondition(creatorId, searchCondition);

        return new FindReviewResponses(reviews);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER);
        });
    }

    private Creator findCreatorById(Long creatorId) {
        return creatorRepository.findById(creatorId).orElseThrow(() -> {
            throw new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR);
        });
    }
}
