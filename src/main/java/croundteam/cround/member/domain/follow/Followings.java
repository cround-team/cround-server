package croundteam.cround.member.domain.follow;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Followings {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "source")
    private List<Follow> followings;

    public Followings(List<Follow> followings) {
        this.followings = followings;
    }
}
