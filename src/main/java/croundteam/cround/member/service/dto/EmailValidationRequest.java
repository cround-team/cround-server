package croundteam.cround.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static croundteam.cround.common.fixtures.ValidationMessages.*;
import static croundteam.cround.common.fixtures.ValidationMessages.MEMBER_EMAIL_MESSAGE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailValidationRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Email(message = EMAIL_FORMAT_MESSAGE)
    @Pattern(regexp = MEMBER_EMAIL_FORMAT, message = MEMBER_EMAIL_MESSAGE)
    private String email;

}
