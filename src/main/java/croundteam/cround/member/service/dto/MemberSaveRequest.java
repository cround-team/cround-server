package croundteam.cround.member.service.dto;

import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.interest.Interest;
import croundteam.cround.security.BCryptEncoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

import static croundteam.cround.common.dto.ValidationMessages.EMPTY_MESSAGE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Email
    private String email;

    @NotBlank(message = EMPTY_MESSAGE)
    private String username;

    @NotBlank(message = EMPTY_MESSAGE)
    private String nickname;

    @NotBlank(message = EMPTY_MESSAGE)
    private String password;

    @NotBlank(message = EMPTY_MESSAGE)
    private String confirmPassword;

    private List<PlatformType> interestPlatform = new ArrayList<>();

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .password(BCryptEncoder.encrypt(password))
                .interest(Interest.from(interestPlatform))
                .build();
    }
}
