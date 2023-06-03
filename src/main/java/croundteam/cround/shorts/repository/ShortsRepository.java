package croundteam.cround.shorts.repository;

import croundteam.cround.shorts.domain.Shorts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShortsRepository extends JpaRepository<Shorts, Long> {

    @Query("SELECT s FROM Shorts s " +
            "join fetch s.creator c " +
            "WHERE s.id = :shortsId")
    Shorts findShortsWithJoinById(@Param("shortsId") Long shortsId);

}
