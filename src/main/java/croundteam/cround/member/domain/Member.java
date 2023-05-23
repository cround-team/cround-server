package croundteam.cround.member.domain;

import croundteam.cround.common.domain.BaseTimeEntity;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.follow.Follow;
import croundteam.cround.member.domain.follow.Followings;
import croundteam.cround.member.domain.interest.Interest;
import lombok.*;

import javax.persistence.*;

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

    @Embedded
    private Interest interest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

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

    public void update(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
    }

    public void follow(Creator target) {
        Follow follow = Follow.of(this, target);
        followings.add(follow);
        target.addFollow(follow);
    }

    public void unfollow(Creator target) {
        Follow follow = Follow.of(this, target);
        followings.remove(follow);
        target.removeFollow(follow);
    }

    public String getRoleName() {
        return role.getName();
    }
}
