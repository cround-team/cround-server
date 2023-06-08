package croundteam.cround.review.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.support.search.SimpleSearchCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

import static croundteam.cround.board.domain.QBoard.board;
import static croundteam.cround.member.domain.QMember.*;
import static croundteam.cround.review.domain.QReview.review;

@Repository
public class ReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Review> findReviewsByCondition(Long creatorId, SimpleSearchCondition searchCondition) {
        return jpaQueryFactory
                .selectFrom(review)
                .join(review.member, member).fetchJoin()
                .where(review.creator.id.eq(creatorId), ltCursorId(searchCondition.getCursorId()))
                .orderBy(review.id.desc())
                .limit(searchCondition.getSize())
                .fetch();
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        if(cursorId == null) {
            return null;
        }
        return board.id.lt(cursorId);
    }
}
