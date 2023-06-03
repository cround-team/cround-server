package croundteam.cround.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.platform.PlatformName;
import croundteam.cround.creator.exception.InvalidSortTypeException;
import croundteam.cround.creator.service.dto.SearchCondition;
import croundteam.cround.shorts.domain.Shorts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static croundteam.cround.creator.domain.QCreator.creator;
import static croundteam.cround.shorts.domain.QShorts.shorts;

@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public void searchByCondition(SearchCondition searchCondition, Pageable pageable) {

    }

    private Slice<Shorts> convertToSliceFromShorts(int page, List<Shorts> shorts, Pageable pageable) {
        boolean hasNext = false;
        if(shorts.size() == page + 1) {
            shorts.remove(page);
            hasNext = true;
        }
        return new SliceImpl<>(shorts, pageable, hasNext);
    }

    private List<Shorts> sort(JPAQuery<Shorts> query, SearchCondition searchCondition) {
        SearchCondition.ContentSortCondition type = searchCondition.getSortTypeByContent();

        switch (type) {
            case LATEST:
                return query
                        .orderBy(shorts.id.desc())
                        .fetch();
            case LIKE:
                return query
                        .orderBy(shorts.shortsLikes.shortsLikes.size().desc(), shorts.id.desc())
                        .fetch();
            case BOOKMARK:
                return query
                        .orderBy(shorts.shortsBookmarks.shortsBookmarks.size().desc(), shorts.id.desc())
                        .fetch();
        }
        throw new InvalidSortTypeException(ErrorCode.INVALID_SORT_TYPE);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if(!StringUtils.hasText(keyword)) {
            return null;
        }
        return shorts.title.value.contains(keyword);
    }

    private BooleanBuilder filterByPlatform(List<String> platforms) {
        if (Objects.isNull(platforms)) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String platform : platforms) {
            booleanBuilder.or(shorts.platformType.platformName.eq(PlatformName.from(platform)));
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
