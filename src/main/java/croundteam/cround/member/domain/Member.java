package croundteam.cround.member.domain;

import croundteam.cround.common.domain.BaseTimeEntity;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.ProfileImageEmptyException;
import croundteam.cround.common.exception.member.ProfileImageEqualsException;
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

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(length = 128)
    private String password;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Embedded
    private Interest interest;

    @Builder
    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = Role.USER;

        this.nickname = createUUID();
        this.profileImage = getDefaultProfileImage();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImage(String profileImage) {
        validateProfileImage(profileImage);
        /**
         * TODO: 이미지를 변경한다. 이미지 변경 정책을 팀 회의때 얘기 나눈다.
         * is null     >> 기본 프로필 이미지로 변경
         * is not null >> 변경할 이미지로 변경
         */
    }

    public void updateInterest(Interest interest) {
        this.interest = interest;
    }

    private void validateProfileImage(String profileImage) {
        if(this.profileImage.equals(profileImage)) {
            throw new ProfileImageEqualsException(ErrorCode.PROFILE_IMAGE_MATCH);
        }
    }

    public String getRoleName() {
        return role.getName();
    }

    private String getDefaultProfileImage() {
        return "https://avatars.githubusercontent.com/u/132455714?s=400&u=b9befff0d433aa7f0eaf9927a4e6f55af3fe9986&v=4";
    }

    private String createUUID() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
