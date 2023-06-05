package croundteam.cround.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static croundteam.cround.common.fixtures.ValidationMessages.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequest {
    @NotBlank(message = EMPTY_MESSAGE)
    @Email(regexp = MEMBER_EMAIL_FORMAT)
    private String email;

    @NotBlank(message = EMPTY_MESSAGE)
//    @Pattern(regexp = MEMBER_PASSWORD_FORMAT, message = MEMBER_PASSWORD_MESSAGE)
    private String password;
}
