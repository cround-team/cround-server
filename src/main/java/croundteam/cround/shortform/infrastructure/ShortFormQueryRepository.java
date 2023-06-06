package croundteam.cround.shortform.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.infrastructure.RepositorySupport;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.creator.exception.InvalidSortTypeException;
import croundteam.cround.shortform.domain.ShortForm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static croundteam.cround.bookmark.domain.QShortFormBookmark.shortFormBookmark;
import static croundteam.cround.common.dto.SearchCondition.ContentSortCondition;
import static croundteam.cround.creator.domain.QCreator.creator;
import static croundteam.cround.like.domain.QShortFormLike.shortFormLike;
import static croundteam.cround.shortform.domain.QShortForm.shortForm;

@Repository
public class ShortFormQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ShortFormQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Slice<ShortForm> searchByCondition(SearchCondition searchCondition, Pageable pageable) {

        JPAQuery<ShortForm> query = jpaQueryFactory
                .selectFrom(shortForm)
                .join(shortForm.creator, creator).fetchJoin()
                .where(
                        ltCursorId(searchCondition.getCursorId()),     // 페이지네이션
                        filterByPlatform(searchCondition.getFilter()), // 필터 플랫폼
                        containsKeyword(searchCondition.getKeyword())) // 검색 조건
                .limit(searchCondition.getSize() + 1);
        List<ShortForm> fetch = sort(query, searchCondition);             // 정렬

        return RepositorySupport.convertToSliceFrom(searchCondition.getSize(), fetch, pageable);
    }

    private List<ShortForm> sort(JPAQuery<ShortForm> query, SearchCondition searchCondition) {
        ContentSortCondition type = searchCondition.getSortTypeByContent();

        switch (type) {
            case LATEST:
                return query
                        .orderBy(shortForm.id.desc())
                        .fetch();
            case LIKE:
                return query
                        .leftJoin(shortForm.shortFormLikes.shortFormLikes, shortFormLike)
                        .groupBy(shortForm)
                        .orderBy(shortFormLike.shortForm.id.sum().desc(), shortForm.id.desc())
                        .fetch();
            case BOOKMARK:
                return query
                        .leftJoin(shortForm.shortsBookmarks.shortsBookmarks, shortFormBookmark)
                        .groupBy(shortForm)
                        .orderBy(shortFormBookmark.id.sum().desc(), shortForm.id.desc())
                        .fetch();
        }
        throw new InvalidSortTypeException(ErrorCode.INVALID_SORT_TYPE);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if(!StringUtils.hasText(keyword)) {
            return null;
        }
        return shortForm.title.value.contains(keyword);
    }

    private BooleanBuilder filterByPlatform(List<String> platforms) {
        if (Objects.isNull(platforms)) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String platform : platforms) {
            booleanBuilder.or(shortForm.platformType.eq(PlatformType.create(platform)));
        }
        return booleanBuilder;
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        if(cursorId == null) {
            return null;
        }
        return creator.id.lt(cursorId);
    }
}