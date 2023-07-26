package croundteam.cround.member.domain;

import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.follow.domain.Followings;
import croundteam.cround.member.application.dto.MemberUpdateRequest;
import croundteam.cround.member.exception.InvalidAuthorizationCodeException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

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

    @Embedded
    private Nickname nickname;

    @Column(length = 128)
    private String password;

    private String authorizationCode;

    @Embedded
    private InterestPlatforms interestPlatforms;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Embedded
    private Followings followings;

    @Builder
    public Member(String email, String username, Nickname nickname, String password,
                  InterestPlatforms interestPlatforms, AuthProvider authProvider) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.interestPlatforms = interestPlatforms;
        this.role = Role.USER;
        this.authProvider = authProvider;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void update(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
    }

    public void updateMember(MemberUpdateRequest memberUpdateRequest) {
        this.nickname = Nickname.create(memberUpdateRequest.getNickname());
        this.interestPlatforms = InterestPlatforms.create(memberUpdateRequest.getInterestPlatforms());
    }

    public boolean isSocial() {
        if (authProvider.isSocial()) {
            return true;
        }
        return false;
    }

    public void follow(Creator target) {
        followings.follow(this, target);
    }

    public void unfollow(Creator target) {
        followings.unfollow(this, target);
    }

    public String getNickname() {
        return nickname.getName();
    }

    public void updateCreatorType() {
        role = Role.CREATOR;
    }

    public String getRoleName() {
        return role.getName();
    }

    public List<String> getInterestPlatforms() {
        return interestPlatforms.castPlatformTypes();
    }

    public void issueAuthorizationCode() {
        this.authorizationCode = UUID.randomUUID().toString();
    }

    public void validateAuthorizationCode(String code) {
        if(code.isBlank() || !code.equals(authorizationCode)) {
            throw new InvalidAuthorizationCodeException(ErrorCode.INVALID_AUTHORIZATION_CODE);
        }
    }

    public boolean isCreator() {
        if(role.isCreator()) {
            return true;
        }
        return false;
    }

    public boolean isSender(Long senderId) {
        if(this.id.equals(senderId)) {
            return true;
        }
        return false;
    }
}
