package croundteam.cround.shorts.domain;

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
    private List<ShortsLike> boardLikes = new ArrayList<>();


}
