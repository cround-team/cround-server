package croundteam.cround.creator.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.service.dto.SearchCreatorCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static croundteam.cround.creator.domain.QCreator.creator;

public class CreatorQueryRepositoryImpl extends QuerydslRepositorySupport implements CreatorQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CreatorQueryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Creator.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 1. 키워드를 검색한다. or 연산으로 creator의 태그와, creator의 이름으로
     */
    @Override
    public Page<Creator> searchCreatorByKeywordAndPlatforms(
            SearchCreatorCondition searchCreatorCondition,
            Pageable pageable
    ) {
        System.out.println("searchCreatorCondition = " + searchCreatorCondition);
        System.out.println("pageable = " + pageable);
        JPAQuery<Creator> query = jpaQueryFactory
                .selectFrom(creator)
                .where(whereKeywords(searchCreatorCondition.getKeyword()));

        List<Creator> creators = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(creators, pageable, creators.size());
    }

    private BooleanExpression whereKeywords(String keyword) {
        /**
         * contains와 like의 차이
         */
        if(!StringUtils.hasText(keyword) || keyword.isEmpty()) {
            return null;
        }
        BooleanExpression containName = creator.platform.platformActivityName.name.contains(keyword);

        return containName;
    }
}
