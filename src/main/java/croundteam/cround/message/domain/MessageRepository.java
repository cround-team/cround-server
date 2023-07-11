package croundteam.cround.message.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.sender = :id OR m.receiver = :id GROUP BY m.receiver ORDER BY m.updatedDate desc")
    List<Message> findMessageBySender(@Param("id") Long id);

    @Query("SELECT m FROM Message m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender) ORDER BY m.id")
    List<Message> findMessageBySenderAndReceiver(@Param("sender") Long sender, @Param("receiver") Long receiver);

}
