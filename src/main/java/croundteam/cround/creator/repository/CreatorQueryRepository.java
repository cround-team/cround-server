package croundteam.cround.creator.repository;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.service.dto.SearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CreatorQueryRepository {
    Slice<Creator> searchByKeywordAndPlatforms(SearchCondition searchCondition, Pageable pageable);
}
