package croundteam.cround.creator.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.InvalidSortTypeException;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformName;
import croundteam.cround.creator.service.dto.SearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static croundteam.cround.common.fixtures.ConstantFixtures.DEFAULT_PAGE_SIZE;
import static croundteam.cround.creator.domain.QCreator.creator;
import static croundteam.cround.creator.domain.tag.QTag.tag;
import static croundteam.cround.creator.service.dto.SearchCondition.CreatorSortCondition;
import static croundteam.cround.member.domain.follow.QFollow.follow;

public class CreatorQueryRepositoryImpl implements CreatorQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CreatorQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Slice<Creator> searchByKeywordAndPlatforms(SearchCondition searchCondition, Pageable pageable) {
        List<Long> tagIds = searchTagByCondition(searchCondition);

        JPAQuery<Creator> query = jpaQueryFactory
                .selectFrom(creator)
                .where(
                        ltCursorId(searchCondition.getCursorId()),   // 페이지네이션
                        wherePlatforms(searchCondition.getFilter()), // 필터 플랫폼
                        searchKeywordInActivityNameAndTagName(searchCondition.getKeyword(), tagIds)) // 검색 조건
//                        containsKeyword(searchCondition.getKeyword()), containsTagIds(tagIds)) // 검색 조건 and 연산
                .limit(DEFAULT_PAGE_SIZE + 1);
        List<Creator> fetch = sort(query, searchCondition);          // 정렬

        return convertToSliceFromCreator(fetch, pageable);
    }

    private List<Creator> sort(JPAQuery<Creator> query, SearchCondition searchCondition) {
        CreatorSortCondition type = searchCondition.getSortType();

        switch (type) {
            case LATEST:
                return query.orderBy(creator.id.desc()).fetch();
            case FOLLOW:
                return query
                        .leftJoin(creator.followers.followers, follow)
                        .groupBy(creator.id)
                        .orderBy(follow.id.count().desc(), creator.id.desc()).fetch();
        }
        throw new InvalidSortTypeException(ErrorCode.INVALID_SORT_TYPE);
    }

    private BooleanBuilder wherePlatforms(List<String> platforms) {
        if (Objects.isNull(platforms)) {
            return null;
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String platform : platforms) {
            booleanBuilder.and(creator.platform.platformHeadType.platformName.eq(PlatformName.from(platform)));
        }
        return booleanBuilder;
    }

    private BooleanBuilder searchKeywordInActivityNameAndTagName(String keyword, List<Long> tagIds) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.hasText(keyword)) {
            booleanBuilder.or(creator.platform.platformActivityName.name.contains(keyword));
        }
        if (Objects.nonNull(tagIds) && tagIds.size() > 0) {
            booleanBuilder.or(creator.creatorTags.creatorTags.any().id.in(tagIds));
        }
        return booleanBuilder;
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        if(cursorId == null) {
            return null;
        }
        return creator.id.lt(cursorId);
    }

    private List<Long> searchTagByCondition(SearchCondition searchCondition) {
        if(!StringUtils.hasText(searchCondition.getKeyword())) {
            return null;
        }
        return jpaQueryFactory
                .select(tag.id)
                .from(tag)
                .where(tag.tagName.name.contains(searchCondition.getKeyword()))
                .fetch();
    }

    private Slice<Creator> convertToSliceFromCreator(List<Creator> creators, Pageable pageable) {
        boolean hasNext = false;
        if(creators.size() == DEFAULT_PAGE_SIZE + 1) {
            creators.remove(DEFAULT_PAGE_SIZE);
            hasNext = true;
        }
        return new SliceImpl<>(creators, pageable, hasNext);
    }
}
