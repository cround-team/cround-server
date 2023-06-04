package croundteam.cround.shorts.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortsSaveRequest {
    private String platformType;
    private String title;
    private String content;
    private String shortsUrl;
    private String thumbnailUrl;
}
