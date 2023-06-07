package croundteam.cround.shortform.infrastructure;

import croundteam.cround.shortform.domain.ShortForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {

    @Query("SELECT s FROM ShortForm s " +
            "join fetch s.creator c " +
            "WHERE s.id = :shortsId")
    ShortForm findShortFormWithJoinById(@Param("shortsId") Long shortsId);

}
