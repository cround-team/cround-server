package croundteam.cround.member.service.dto;

import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.interest.InterestPlatforms;
import croundteam.cround.security.BCryptEncoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

import static croundteam.cround.common.dto.ValidationMessages.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Email(message = EMAIL_FORMAT_MESSAGE)
    @Pattern(regexp = MEMBER_EMAIL_FORMAT, message = MEMBER_EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_NAME_FORMAT, message = MEMBER_NAME_MESSAGE)
    private String username;

    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_NICKNAME_FORMAT, message = MEMBER_NICKNAME_MESSAGE)
    private String nickname;

    @NotBlank(message = EMPTY_MESSAGE)
    private String password;

    @NotBlank(message = EMPTY_MESSAGE)
    private String confirmPassword;

    private List<PlatformType> platformTypes = new ArrayList<>();

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .password(BCryptEncoder.encrypt(password))
                .interest(InterestPlatforms.create(platformTypes))
                .build();
    }
}
