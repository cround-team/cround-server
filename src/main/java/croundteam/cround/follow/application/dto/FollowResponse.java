package croundteam.cround.follow.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {

    private Long sourceId;
    private Long targetId;

}
