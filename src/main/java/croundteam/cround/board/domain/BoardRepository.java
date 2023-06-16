package croundteam.cround.board.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b " +
            "join fetch b.creator c " +
            "WHERE b.id = :boardId")
    Optional<Board> findBoardById(@Param("boardId") Long boardId);
}
