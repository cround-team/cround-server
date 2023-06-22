package croundteam.cround.member.application.dto;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

import static croundteam.cround.common.fixtures.ValidationMessages.*;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_NICKNAME_FORMAT, message = MEMBER_NICKNAME_MESSAGE)
    private String nickname;

    private List<PlatformType> interestPlatforms = new ArrayList<>();

    public MemberUpdateRequest(String nickname, List<PlatformType> interestPlatforms) {
        this.nickname = nickname;
        this.interestPlatforms = interestPlatforms;
    }
}
