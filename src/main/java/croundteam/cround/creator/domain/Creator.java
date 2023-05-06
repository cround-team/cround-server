package croundteam.cround.creator.domain;

import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(
        name = "creator_member_unique",
        columnNames="member_id"))
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_creator_to_member"))
    private Member member;

    @Embedded
    private Platform platform;


}
