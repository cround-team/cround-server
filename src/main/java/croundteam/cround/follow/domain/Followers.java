package croundteam.cround.follow.domain;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.follow.exception.InvalidFollowException;
import croundteam.cround.member.domain.Member;
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
public class Followers {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    public void add(Follow follow) {
        validateFollow(follow);
        followers.add(follow);
    }

    public void remove(Follow follow) {
        followers.remove(follow);
    }

    private void validateFollow(Follow follow) {
        if(followers.contains(follow)) {
            throw new InvalidFollowException(ErrorCode.DUPLICATE_FOLLOW);
        }
    }

    public boolean isFollowedBy(Creator creator, Member member) {
        Follow follow = new Follow(member, creator);
        return followers.contains(follow);
    }

    public int getFollowersCount() {
        return followers.size();
    }
}
