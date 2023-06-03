package croundteam.cround.shorts.domain;

import croundteam.cround.board.exception.InvalidLikeException;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsLikes {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shorts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShortsLike> shortsLikes = new ArrayList<>();

    public void addLike(Shorts shorts, Member member) {
        ShortsLike like = ShortsLike.of(shorts, member);
        validateLike(like);
        shortsLikes.add(like);
    }

    public void removeLike(Shorts shorts, Member member) {
        ShortsLike like = ShortsLike.of(shorts, member);
        shortsLikes.remove(like);
    }

    private void validateLike(ShortsLike like) {
        if(shortsLikes.contains(like)) {
            throw new InvalidLikeException(ErrorCode.DUPLICATE_LIKE);
        }
    }

    public boolean isLikedBy(Shorts shorts, Member member) {
        ShortsLike shortsLike = ShortsLike.of(shorts, member);
        return shortsLikes.contains(shortsLike);
    }

    public int getShortsLikes() {
        return shortsLikes.size();
    }
}
