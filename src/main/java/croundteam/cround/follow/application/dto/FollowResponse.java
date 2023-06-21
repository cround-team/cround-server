package croundteam.cround.follow.application.dto;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowResponse {

    private boolean isFollowed;

    public FollowResponse(Member source, Creator target) {
        this.isFollowed = target.isFollowedBy(source);
    }
}
