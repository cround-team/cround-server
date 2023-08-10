package croundteam.cround.creator.domain;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.creator.exception.InvalidSortTypeException;
import croundteam.cround.support.search.BaseSearchCondition;
import croundteam.cround.support.search.SearchCondition;
import io.jsonwebtoken.lang.Collections;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static croundteam.cround.creator.domain.QCreator.creator;
import static croundteam.cround.creator.domain.tag.QTag.tag;
import static croundteam.cround.follow.domain.QFollow.follow;
import static croundteam.cround.member.domain.QMember.member;
import static croundteam.cround.support.RepositorySupport.convertToSliceFrom;
import static croundteam.cround.support.search.SearchCondition.CreatorSortCondition;

@Repository
public class CreatorQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CreatorQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Slice<Creator> searchByCondition(SearchCondition searchCondition, Pageable pageable) {
        JPAQuery<Creator> query = jpaQueryFactory
                .selectFrom(creator)
                .where(
                        ltCursorId(searchCondition.getCursorId()),     // 페이지네이션
                        filterByPlatform(searchCondition.getFilter()), // 필터 플랫폼
                        containsKeyword(searchCondition.getKeyword())) // 검색 조건
//                        containsKeyword(searchCondition.getKeyword()), containsTagIds(tagIds)) // 검색 조건 and 연산
                .limit(searchCondition.getSize() + 1);
        List<Creator> fetch = sort(query, searchCondition);            // 정렬

        return convertToSliceFrom(searchCondition.getSize(), fetch, pageable);
    }

    private List<Creator> sort(JPAQuery<Creator> query, SearchCondition searchCondition) {
        CreatorSortCondition type = searchCondition.getSortTypeByCreator();

        switch (type) {
            case LATEST:
                return query
                        .orderBy(creator.id.desc())
                        .fetch();
            case FOLLOW:
                return query
                        .leftJoin(creator.followers.followers, follow)
                        .groupBy(creator)
                        .orderBy(follow.id.sum().desc(), creator.id.desc())
                        .fetch();
            case REVIEW:
                return query
                        .orderBy(creator.avgRating.desc(), creator.id.desc())
                        .fetch();
        }
        throw new InvalidSortTypeException(ErrorCode.INVALID_SORT_TYPE);
    }

    public Slice<Creator> findOwnFollowingBy(Long memberId, BaseSearchCondition searchCondition) {
        List<Creator> creators = jpaQueryFactory
                .select(creator)
                .from(creator, creator)
                .join(creator.followers.followers, follow)
                .groupBy(creator.id)
                .where(follow.source.id.eq(memberId), ltCursorId(searchCondition.getCursorId()))
                .orderBy(follow.id.desc())
                .limit(searchCondition.getSize() + 1)
                .fetch();
        return convertToSliceFrom(searchCondition.getSize(), creators, Pageable.unpaged());
    }

    public List<Creator> findCreatorByInterestPlatform(int size, List<String> interestPlatforms) {

        List<Long> ids = jpaQueryFactory
                .select(creator.id)
                .from(creator)
                .where(filterByPlatform(interestPlatforms))
                .groupBy(creator.id)
                .fetch();

        List<Long> randoms = createRandomBy(ids, size);

        return jpaQueryFactory
                .selectFrom(creator)
                .join(creator.member, member).fetchJoin()
                .where(creator.id.in(randoms))
                .fetch();
    }

    private BooleanBuilder filterByPlatform(List<String> platforms) {
        if (Collections.isEmpty(platforms)) return null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String platform : platforms) {
            booleanBuilder.or(creator.platform.platformHeadType.eq(PlatformType.create(platform)));
        }
        return booleanBuilder;
    }

    private List<Long> createRandomBy(List<Long> ids, int size) {
        Set<Long> randoms = new HashSet<>();

        if(size > ids.size()) size = ids.size();

        while (randoms.size() < size) {
            int random = (int) (Math.random() * ids.size()); // 0 ~ 11
            Long e = ids.get(random);

            randoms.add(e);
        }
        return new ArrayList<>(randoms);
    }

    private BooleanBuilder containsKeyword(String keyword) {
        if(!StringUtils.hasText(keyword)) {
            return null;
        }
        return new BooleanBuilder()
                .or(containActivityName(keyword))
                .or(containTags(keyword));
    }

    private BooleanExpression containTags(String keyword) {
        List<Long> tagIds = jpaQueryFactory
                .select(tag.id)
                .from(tag)
                .where(tag.tagName.name.contains(keyword))
                .fetch();

        if(CollectionUtils.isEmpty(tagIds)) {
            return null;
        }
        return creator.creatorTags.creatorTags.any().id.in(tagIds);
    }

    private BooleanExpression containActivityName(String keyword) {
        return creator.nickname.name.contains(keyword);
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        if(cursorId == null) {
            return null;
        }
        return creator.id.lt(cursorId);
    }
}
