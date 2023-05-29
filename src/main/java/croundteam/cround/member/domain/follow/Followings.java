package croundteam.cround.member.domain.follow;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.InvalidFollowException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Followings {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "source", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Follow> followings = new ArrayList<>();

    public void follow(Member member, Creator target) {
        Follow follow = Follow.of(member, target);
        validateFollow(follow);

        followings.add(follow);
        target.addFollow(follow);
    }

    public void unfollow(Member member, Creator target) {
        Follow follow = Follow.of(member, target);
        followings.remove(follow);
        target.removeFollow(follow);
    }

    private void validateFollow(Follow follow) {
        if(followings.contains(follow)) {
            throw new InvalidFollowException(ErrorCode.INVALID_FOLLOW);
        }
    }
}
