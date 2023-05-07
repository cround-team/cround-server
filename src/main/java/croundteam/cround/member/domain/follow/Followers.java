package croundteam.cround.member.domain.follow;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Followers {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
    private List<Follow> followers;

    public Followers(List<Follow> followers) {
        this.followers = followers;
    }
}
