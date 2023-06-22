package croundteam.cround.creator.domain;

import croundteam.cround.creator.domain.tag.CreatorTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreatorTagRepository extends JpaRepository<CreatorTag, Long> {

    @Query("SELECT c FROM CreatorTag c " +
            "join fetch c.tag t " +
            "WHERE c.creator.id = :creatorId")
    List<CreatorTag> findCreatorTagById(@Param("creatorId") Long creatorId);

    @Modifying
    @Query("DELETE FROM CreatorTag c WHERE c.creator = :creator")
    void deleteByCreator(@Param("creator") Creator creator);

}
