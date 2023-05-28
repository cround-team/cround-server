package croundteam.cround.creator.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.service.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static croundteam.cround.creator.domain.QCreator.creator;
import static croundteam.cround.creator.domain.tag.QTag.tag;
import static croundteam.cround.member.domain.follow.QFollow.follow;

@Repository
@RequiredArgsConstructor
public class CreatorOffsetRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Creator> searchPageByKeywordAndPlatforms(SearchCondition searchCondition, Pageable pageable) {
        List<Long> tagIds = jpaQueryFactory
                .select(tag.id)
                .from(tag)
                .where(containsTagName(searchCondition.getKeyword()))
                .fetch();

        List<Creator> creators = getCreatorByCondition(searchCondition, tagIds);

        return new PageImpl<>(creators, pageable, creators.size());
    }

    private List<Creator> getCreatorByCondition(SearchCondition searchCondition, List<Long> tagIds) {
        switch (searchCondition.getCondition()) {
            case "FOLLOW":
                return getCreatorByOrderByFollow(searchCondition, tagIds).fetch();
            case "LATEST":
                return getCreatorByOrderByLatest(searchCondition, tagIds).fetch();
            default:
                throw new IllegalStateException("Unexpected value: " + searchCondition.getCondition());
        }
    }

    private JPAQuery<Creator> getCreatorByOrderByLatest(SearchCondition searchCondition, List<Long> tagIds) {
        return jpaQueryFactory
                .selectFrom(creator)
                .where(whereKeywords(searchCondition.getKeyword(), tagIds))
                .offset(searchCondition.getPage())
                .limit(searchCondition.getDEFAULT_SIZE())
                .orderBy(creator.id.desc());
    }

    private JPAQuery<Creator> getCreatorByOrderByFollow(SearchCondition searchCondition, List<Long> tagIds) {
        JPAQuery<Long> ids = jpaQueryFactory
                .select(follow.target.id)
                .from(follow)
                .groupBy(follow.target.id)
                .orderBy(follow.target.id.desc())
                .offset(searchCondition.getPage())
                .limit(searchCondition.getDEFAULT_SIZE());

        return jpaQueryFactory
                .selectFrom(creator)
                .where(whereKeywords(searchCondition.getKeyword(), tagIds), creator.id.in(ids))
                .orderBy(creator.id.desc());

        /** << Before >>
         * return jpaQueryFactory
         *     .selectFrom(creator)
         *     .leftJoin(creator.followers.followers, follow)
         *     .where(whereKeywords(searchCondition.getKeyword(), tagIds))
         *     .groupBy(creator)
         *     .orderBy(follow.count().desc(), creator.id.asc());
         */
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
            BooleanExpression containTagName = creator.creatorTags.creatorTags.any().id.in(tagIds);

            return containName.or(containTagName);
        }

}
