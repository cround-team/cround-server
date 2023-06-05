package croundteam.cround.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.board.domain.Board;
import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.platform.PlatformName;
import croundteam.cround.creator.exception.InvalidSortTypeException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static croundteam.cround.board.domain.QBoard.board;
import static croundteam.cround.common.dto.SearchCondition.ContentSortCondition;
import static croundteam.cround.common.repository.RepositorySupport.convertToSliceFrom;

@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Slice<Board> searchByCondition(SearchCondition searchCondition, Pageable pageable) {
        JPAQuery<Board> query = jpaQueryFactory
                .select(board)
                .from(board, board)
                .where(
                        ltCursorId(searchCondition.getCursorId()),     // 페이지네이션
                        filterByPlatform(searchCondition.getFilter()), // 필터 플랫폼
                        containsKeyword(searchCondition.getKeyword())) // 검색 조건
                .limit(searchCondition.getSize() + 1);
        List<Board> fetch = sort(query, searchCondition);             // 정렬

        return convertToSliceFrom(searchCondition.getSize(), fetch, pageable);
    }

    private List<Board> sort(JPAQuery<Board> query, SearchCondition searchCondition) {
        ContentSortCondition type = searchCondition.getSortTypeByContent();

        switch (type) {
            case LATEST:
                return query
                        .orderBy(board.id.desc())
                        .fetch();
            case LIKE:
                return query
                        .orderBy(board.boardLikes.boardLikes.size().desc(), board.id.desc())
                        .fetch();
            case BOOKMARK:
                return query
                        .orderBy(board.boardBookmarks.boardBookmarks.size().desc(), board.id.desc())
                        .fetch();
        }
        throw new InvalidSortTypeException(ErrorCode.INVALID_SORT_TYPE);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if(!StringUtils.hasText(keyword)) {
            return null;
        }
        return board.title.value.contains(keyword);
    }

    private BooleanBuilder filterByPlatform(List<String> platforms) {
        if (platforms.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String platform : platforms) {
            booleanBuilder.or(board.platformType.platformName.eq(PlatformName.from(platform)));
        }
        return booleanBuilder;
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        if(cursorId == null) {
            return null;
        }
        return board.id.lt(cursorId);
    }
}
