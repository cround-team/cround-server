package croundteam.cround.creator.repository;

import croundteam.cround.creator.domain.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, Long> {
    boolean existByPlatformActivityName(String platformActivityName);
}
