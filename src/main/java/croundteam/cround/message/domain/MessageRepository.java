package croundteam.cround.message.domain;

import croundteam.cround.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m " +
            "FROM Message m " +
            "join fetch m.receiver r " +
            "WHERE m.sender = :member OR m.receiver = :member " +
            "GROUP BY m.receiver " +
            "ORDER BY m.updatedDate")
    List<Message> findMessageBy(@Param("member") Member member);

    @Query("SELECT m FROM Message m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender) ORDER BY m.id")
    List<Message> findMessageBySenderAndReceiver(@Param("sender") Member sender, @Param("receiver") Member receiver);

}
