package croundteam.cround.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static croundteam.cround.common.dto.ValidatorMessage.EMPTY_MESSAGE;

@Getter
@NoArgsConstructor
@ToString
public class MemberSaveRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    private String userName;

    @NotBlank(message = EMPTY_MESSAGE)
    @Email
    private String email;

    @NotBlank(message = EMPTY_MESSAGE)
    private String password;

    public MemberSaveRequest(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
