package croundteam.cround.member.domain;

import croundteam.cround.common.domain.BaseTimeEntity;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.ProfileImageEmptyException;
import croundteam.cround.common.exception.member.ProfileImageEqualsException;
import croundteam.cround.member.domain.follow.Followings;
import croundteam.cround.member.domain.interest.Interest;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
        name = "member_email_unique",
        columnNames="email"))
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(length = 20)
    private String nickname;

    @Column(length = 128)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Embedded
    private Interest interest;

    @Embedded
    private Followings followings;

    @Builder
    public Member(String email, String username, String nickname, String password, Interest interest) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.interest = interest;
        this.role = Role.USER;
    }

    public String getRoleName() {
        return role.getName();
    }

    public void update(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
    }
}
