package croundteam.cround.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static croundteam.cround.common.fixtures.ValidationMessages.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameValidationRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_NICKNAME_FORMAT, message = MEMBER_NICKNAME_MESSAGE)
    private String nickname;
}
