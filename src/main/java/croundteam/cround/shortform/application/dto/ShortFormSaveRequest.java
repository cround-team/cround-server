package croundteam.cround.shortform.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortFormSaveRequest {
    private String platformType;
    private String title;
    private String content;
    private String shortFormUrl;
    private String thumbnailUrl;
}
