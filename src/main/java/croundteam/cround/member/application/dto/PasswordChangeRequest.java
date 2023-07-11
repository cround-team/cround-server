package croundteam.cround.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static croundteam.cround.common.fixtures.ValidationMessages.EMPTY_MESSAGE;
import static croundteam.cround.common.fixtures.ValidationMessages.ID_TEXT_EMPTY_MESSAGE;

@Getter
@NoArgsConstructor
public class PasswordChangeRequest {

    @NotNull(message = ID_TEXT_EMPTY_MESSAGE)
    private Long id;

    @NotBlank(message = EMPTY_MESSAGE)
    private String code;

    @NotBlank(message = EMPTY_MESSAGE)
//    @Pattern(regexp = MEMBER_PASSWORD_FORMAT, message = MEMBER_PASSWORD_MESSAGE)
    private String password;

    @NotBlank(message = EMPTY_MESSAGE)
//    @Pattern(regexp = MEMBER_PASSWORD_FORMAT, message = MEMBER_PASSWORD_MESSAGE)
    private String confirmPassword;

    public PasswordChangeRequest(Long id, String code, String password, String confirmPassword) {
        this.id = id;
        this.code = code;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
