package croundteam.cround.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "join fetch r.member m " +
            "join fetch r.creator c " +
            "WHERE r.creator.id = :creatorId " +
            "ORDER BY r.id DESC")
    List<Review> findReviewsByCreatorId(@Param("creatorId") Long creatorId);

}
