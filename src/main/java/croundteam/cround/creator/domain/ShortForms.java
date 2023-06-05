package croundteam.cround.creator.domain;

import croundteam.cround.shortform.domain.ShortForm;
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
public class ShortForms {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<ShortForm> shortForms = new ArrayList<>();

    public void add(ShortForm shorts) {
        shortForms.add(shorts);
    }
}
