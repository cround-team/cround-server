package croundteam.cround.creator.repository;

import croundteam.cround.creator.domain.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CreatorRepository extends JpaRepository<Creator, Long> {

    boolean existsByPlatformPlatformActivityNameName(String platformActivityName);

    @Query("SELECT c FROM Creator c WHERE c.member.id = :memberId")
    Optional<Creator> findCreatorByMemberId(@Param("memberId") Long memberId);
}
