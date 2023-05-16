package croundteam.cround.member.domain.follow;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.InvalidFollowException;
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

    public void add(Follow follow) {
        followings.add(follow);
    }

    public void remove(Follow follow) {
        validateFollow(follow);
        followings.remove(follow);
    }

    private void validateFollow(Follow follow) {
        if(!followings.contains(follow)) {
            throw new InvalidFollowException(ErrorCode.INVALID_FOLLOW);
        }
    }
}
