package croundteam.cround.creator.domain.platform;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Platform {

    @Embedded
    private PlatformUrl platformUrl;

    @Embedded
    private PlatformType platformType;

}
