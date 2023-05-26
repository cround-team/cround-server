package croundteam.cround.creator.domain;

import croundteam.cround.shorts.domain.Shorts;
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
public class ShortClass {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<Shorts> shorts = new ArrayList<>();

    public void add(Shorts shorts) {
        this.shorts.add(shorts);
    }
}
