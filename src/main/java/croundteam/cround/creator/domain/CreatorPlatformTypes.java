package croundteam.cround.creator.domain;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatorPlatformTypes {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "creator_platform",
            joinColumns = @JoinColumn(
                    name = "creator_id"))
    private List<PlatformType> platformTypes = new ArrayList<>();

    public CreatorPlatformTypes(List<PlatformType> platformTypes) {
        this.platformTypes = platformTypes;
    }

    public static CreatorPlatformTypes create(List<PlatformType> platformTypes) {
        return new CreatorPlatformTypes(platformTypes);
    }

    public List<PlatformType> getPlatformTypes() {
        return platformTypes;
    }
}
