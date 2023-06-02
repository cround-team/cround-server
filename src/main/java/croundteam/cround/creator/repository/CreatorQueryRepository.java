package croundteam.cround.creator.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformName;
import croundteam.cround.creator.exception.InvalidSortTypeException;
import croundteam.cround.creator.service.dto.SearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static croundteam.cround.common.fixtures.ConstantFixtures.DEFAULT_PAGE_SIZE;
import static croundteam.cround.creator.domain.QCreator.creator;
import static croundteam.cround.creator.domain.tag.QTag.tag;
import static croundteam.cround.creator.service.dto.SearchCondition.CreatorSortCondition;
import static croundteam.cround.member.domain.follow.QFollow.follow;

@Repository
public class CreatorQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CreatorQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Slice<Creator> searchByKeywordAndPlatforms(SearchCondition searchCondition, Pageable pageable) {
        JPAQuery<Creator> query = jpaQueryFactory
                .selectFrom(creator)
                .where(
                        ltCursorId(searchCondition.getCursorId()),     // 페이지네이션
                        filterByPlatform(searchCondition.getFilter()), // 필터 플랫폼
                        containsKeyword(searchCondition.getKeyword())) // 검색 조건
//                        containsKeyword(searchCondition.getKeyword()), containsTagIds(tagIds)) // 검색 조건 and 연산
                .limit(searchCondition.getPage() + 1);
        List<Creator> fetch = sort(query, searchCondition);            // 정렬

        return convertToSliceFromCreator(searchCondition.getPage(), fetch, pageable);
    }

    private Slice<Creator> convertToSliceFromCreator(int page, List<Creator> creators, Pageable pageable) {
        boolean hasNext = false;
        if(creators.size() == page + 1) {
            creators.remove(page);
            hasNext = true;
        }
        return new SliceImpl<>(creators, pageable, hasNext);
    }

    private List<Creator> sort(JPAQuery<Creator> query, SearchCondition searchCondition) {
        CreatorSortCondition type = searchCondition.getSortType();

        switch (type) {
            case LATEST:
                return query
                        .orderBy(creator.id.desc())
                        .fetch();
            case FOLLOW:
                return query
                        .leftJoin(creator.followers.followers, follow)
                        .groupBy(creator.id)
                        .orderBy(follow.id.count().desc(), creator.id.desc())
                        .fetch();
        }
        throw new InvalidSortTypeException(ErrorCode.INVALID_SORT_TYPE);
    }

    private BooleanBuilder filterByPlatform(List<String> platforms) {
        if (Objects.isNull(platforms)) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String platform : platforms) {
            booleanBuilder.and(creator.platform.platformHeadType.platformName.eq(PlatformName.from(platform)));
        }
        return booleanBuilder;
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

        if(Objects.isNull(tagIds) || tagIds.size() < 1) {
            return null;
        }
        return creator.creatorTags.creatorTags.any().id.in(tagIds);
    }

    private BooleanExpression containActivityName(String keyword) {
        return creator.platform.platformActivityName.name.contains(keyword);
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        if(cursorId == null) {
            return null;
        }
        return creator.id.lt(cursorId);
    }
}
