package croundteam.cround.creator.repository;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.service.dto.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CreatorQueryRepository {

    Page<Creator> searchCreatorByKeywordAndPlatforms(SearchCondition searchCondition, Pageable pageable);

}
