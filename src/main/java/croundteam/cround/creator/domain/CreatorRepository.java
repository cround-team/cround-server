package croundteam.cround.creator.domain;

import croundteam.cround.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreatorRepository extends JpaRepository<Creator, Long> {

    boolean existsByNicknameName(@Param("nickname") String nickname);

    @Query("SELECT c FROM Creator c WHERE c.member.email = :email")
    Optional<Creator> findCreatorByMember(@Param("email") String email);

    @Query("SELECT c FROM Creator c " +
            "join fetch c.member m " +
            "join fetch c.activityPlatforms.platformTypes " +
            "WHERE c.id = :creatorId")
    Optional<Creator> findCreatorById(@Param("creatorId") Long creatorId);

    @Query("SELECT c FROM Creator c " +
            "ORDER BY c.id desc")
    List<Creator> findCreatorSortByLatest(Pageable pageable);

    Long countBy();

    @Query("SELECT c FROM Creator c WHERE c.id IN (:randomBy)")
    List<Creator> findCreatorByRandom(@Param("randomBy") List<Long> randomBy);

    @Query("SELECT c FROM Creator c " +
            "join fetch c.member m " +
            "join fetch c.activityPlatforms.platformTypes " +
            "WHERE c.member = :member")
    Optional<Creator> findCreatorByMember(@Param("member") Member member);

}
