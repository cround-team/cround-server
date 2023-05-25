package croundteam.cround.creator.service.dto;

import croundteam.cround.creator.domain.platform.PlatformType;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class SearchCreatorCondition {
    // private List<PlatformType> platformTypes;
    private String keyword;
}
