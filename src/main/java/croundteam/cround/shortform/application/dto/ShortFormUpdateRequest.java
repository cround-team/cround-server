package croundteam.cround.shortform.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShortFormUpdateRequest {

    private String title;
    private String content;
    private String platformType;
}
