package croundteam.cround.shorts.repository;

import croundteam.cround.shorts.domain.Shorts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortsRepository extends JpaRepository<Shorts, Long> {
}
