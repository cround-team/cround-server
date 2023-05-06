package croundteam.cround.creator.domain.platform;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class PlatformType {

    @Enumerated(EnumType.STRING)
    private PlatformName platformName;


}
