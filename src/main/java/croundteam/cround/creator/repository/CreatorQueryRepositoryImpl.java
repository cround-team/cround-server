package croundteam.cround.creator.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.creator.domain.Creator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static croundteam.cround.creator.domain.QCreator.creator;
import static croundteam.cround.creator.domain.tag.QTag.tag;

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
    public Page<Creator> searchCreatorByKeywordAndPlatforms(List<String> platforms, String keyword, Pageable pageable) {
        System.out.println("platforms = " + platforms);
        System.out.println("keyword = " + keyword);
        System.out.println("pageable = " + pageable);

        List<Long> tagIds = jpaQueryFactory
                .select(tag.id)
                .from(tag)
                .where(containsTagName(keyword))
                .fetch();

        JPAQuery<Creator> query = jpaQueryFactory
                .selectFrom(creator)
                .where(whereKeywords(keyword, tagIds));

        List<Creator> creators = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(creators, pageable, creators.size());
    }

    private BooleanExpression containsTagName(String keyword) {
        if(!StringUtils.hasText(keyword) || keyword.isEmpty()) {
            return null;
        }
        return tag.tagName.name.contains(keyword);
    }

    private BooleanExpression whereKeywords(String keyword, List<Long> tagIds) {
         // contains와 like의 차이
        if(!StringUtils.hasText(keyword) || keyword.isEmpty()) {
            return null;
        }
        BooleanExpression containName = creator.platform.platformActivityName.name.contains(keyword);
        BooleanExpression containTagName = creator.creatorTags.any().id.in(tagIds);

        return containName.or(containTagName);
    }
}
