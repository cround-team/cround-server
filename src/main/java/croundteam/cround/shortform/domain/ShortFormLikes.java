package croundteam.cround.shortform.domain;

import croundteam.cround.board.exception.InvalidLikeException;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.like.domain.ShortFormLike;
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
public class ShortFormLikes {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shortForm", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShortFormLike> shortFormLikes = new ArrayList<>();

    public void addLike(ShortForm shortForm, Member member) {
        ShortFormLike like = new ShortFormLike(shortForm, member);
        validateLike(like);
        shortFormLikes.add(like);
    }

    public void removeLike(ShortForm shortForm, Member member) {
        ShortFormLike like = new ShortFormLike(shortForm, member);
        shortFormLikes.remove(like);
    }

    private void validateLike(ShortFormLike like) {
        if(shortFormLikes.contains(like)) {
            throw new InvalidLikeException(ErrorCode.DUPLICATE_LIKE);
        }
    }

    public boolean isLikedBy(ShortForm shorts, Member member) {
        ShortFormLike shortsLike = new ShortFormLike(shorts, member);
        return shortFormLikes.contains(shortsLike);
    }

    public int getLikes() {
        return shortFormLikes.size();
    }
}
