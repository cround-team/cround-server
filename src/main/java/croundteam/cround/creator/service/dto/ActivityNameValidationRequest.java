package croundteam.cround.creator.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static croundteam.cround.common.fixtures.ValidationMessages.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityNameValidationRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = CREATOR_ACTIVITY_NAME_FORMAT, message = CREATOR_ACTIVITY_NAME_MESSAGE)
    private String platformActivityName;
}
