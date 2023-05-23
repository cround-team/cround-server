package croundteam.cround.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static croundteam.cround.common.dto.ValidationMessages.EMPTY_MESSAGE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameValidationRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    private String nickname;
}
