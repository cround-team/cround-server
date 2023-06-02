package croundteam.cround.member.domain;

import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.follow.Followings;
import croundteam.cround.member.domain.interest.InterestPlatforms;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(
        name = "member_email_unique",
        columnNames="email"))
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(length = 20)
    private String username;

    @Column(length = 20)
    private String nickname;

    @Column(length = 128)
    private String password;

    @Embedded
    private InterestPlatforms interest;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Embedded
    private Followings followings;

    @Builder
    public Member(String email, String username, String nickname, String password, InterestPlatforms interest,
                  AuthProvider authProvider) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.interest = interest;
        this.role = Role.USER;
        this.authProvider = authProvider;
    }

    public void update(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
    }

    public void follow(Creator target) {
        followings.follow(this, target);
    }

    public void unfollow(Creator target) {
        followings.unfollow(this, target);
    }

    public String getRoleName() {
        return role.getName();
    }
}
