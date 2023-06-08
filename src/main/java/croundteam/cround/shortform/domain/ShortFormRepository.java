package croundteam.cround.shortform.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {

    @Query("SELECT s FROM ShortForm s " +
            "join fetch s.creator c " +
            "WHERE s.id = :shortsId")
    ShortForm findShortFormWithJoinById(@Param("shortsId") Long shortsId);

    @Query("SELECT s FROM ShortForm s " +
            "join s.shortFormLikes.shortFormLikes as likes " +
            "on likes.member.id = :memberId " +
            "group by s ")
    List<ShortForm> findOwnBookmarkBy(@Param("memberId") Long memberId);

}
