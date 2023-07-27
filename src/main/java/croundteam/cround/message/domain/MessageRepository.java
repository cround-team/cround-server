package croundteam.cround.message.domain;

import croundteam.cround.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * 1. group by를 했을 때 최신 쪽지를 반환해야 함
     * 2. 읽지 않은 쪽지의 개수를 반환해야 함
     * 3. 로그인한 사용자가 한번이라고 쪽지한 상대방 목록을 나타내야 함
     */
    @Query(value = "" +
            "SELECT * " +
            "FROM message " +
            "WHERE (receiver, updated_date) IN " +
                "(SELECT receiver, MAX(updated_date) " +
                "FROM message " +
                "WHERE sender = :member OR receiver = :member " +
                "GROUP BY receiver) " +
            "order by updated_date desc", nativeQuery = true)
    List<Message> findMessageByMember(@Param("member") Member member);

    @Query("SELECT m FROM Message m WHERE (m.sender = :sender AND m.receiver = :receiver) OR (m.sender = :receiver AND m.receiver = :sender) ORDER BY m.id")
    List<Message> findMessageBySenderAndReceiver(@Param("sender") Member sender, @Param("receiver") Member receiver);

    @Modifying
    @Query("UPDATE Message m SET m.readStatus = 'READ' WHERE m.receiver = :sender and m.sender = :receiver")
    void updateMessageReadStatusByReceiver(@Param("sender") Member sender, @Param("receiver") Member receiver);

}
