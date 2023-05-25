package croundteam.cround.creator.repository;

import croundteam.cround.creator.domain.Creator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CreatorQueryRepository {

    Page<Creator> searchCreatorByKeywordAndPlatforms(List<String> platforms, String keyword, Pageable pageable);

}
