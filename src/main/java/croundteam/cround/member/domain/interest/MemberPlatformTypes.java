package croundteam.cround.member.domain.interest;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPlatformTypes {

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_platform",
            joinColumns = @JoinColumn(
                    name = "member_id"))
    private List<PlatformType> platformTypes = new ArrayList<>();

    public MemberPlatformTypes(List<PlatformType> platformTypes) {
        this.platformTypes = platformTypes;
    }

    public static MemberPlatformTypes create(List<PlatformType> platformTypes) {
        return new MemberPlatformTypes(platformTypes);
    }
}
