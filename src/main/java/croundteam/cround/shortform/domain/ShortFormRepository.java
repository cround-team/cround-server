package croundteam.cround.shortform.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {

    @Query("SELECT s FROM ShortForm s " +
            "join fetch s.creator c " +
            "join fetch c.member m " +
            "WHERE s.id = :shortsId")
    Optional<ShortForm> findShortFormById(@Param("shortsId") Long shortsId);

    @Query("SELECT s FROM ShortForm s " +
            "left join s.shortFormLikes.shortFormLikes l " +
            "left join s.shortsBookmarks.shortsBookmarks b " +
            "where s.id = :shortsId " +
            "group by s")
    Optional<ShortForm> findShortFormWithLikeAndBookmarkById(@Param("shortsId") Long shortsId);

}
