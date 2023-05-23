package croundteam.cround.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static croundteam.cround.common.dto.ValidationMessages.EMPTY_MESSAGE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequest {
    @NotBlank(message = EMPTY_MESSAGE)
    @Email
    private String email;

    @NotBlank(message = EMPTY_MESSAGE)
    private String password;
}
