package croundteam.cround.member.application.dto;

import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindMemberResponse {

    private String nickname;
    private List<String> interestPlatforms;

    public FindMemberResponse(Member member) {
        this.nickname = member.getNickname();
        this.interestPlatforms = member.getInterestPlatforms();
    }
}
