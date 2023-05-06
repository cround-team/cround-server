package croundteam.cround.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static croundteam.cround.common.dto.ValidatorMessage.EMPTY_MESSAGE;

@Getter
@NoArgsConstructor
public class MemberSaveRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    private String username;

    @NotBlank(message = EMPTY_MESSAGE)
    @Email
    private String email;

    @NotBlank(message = EMPTY_MESSAGE)
    private String password;

    @NotBlank(message = EMPTY_MESSAGE)
    private String confirmPassword;

    public MemberSaveRequest(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
