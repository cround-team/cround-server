package croundteam.cround.shortform.application.dto;

import croundteam.cround.board.domain.Content;
import croundteam.cround.board.domain.Title;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.domain.ShortFormUrl;
import croundteam.cround.shortform.domain.ThumbnailImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortFormSaveRequest {
    private String title;
    private String content;
    private String platformType;
    private String shortFormUrl;

    public ShortForm toEntity() {
        return ShortForm.builder()
                .title(Title.create(title))
                .content(Content.create(content))
                .platformType(PlatformType.create(platformType))
                .shortFormUrl(ShortFormUrl.create(shortFormUrl))
                .build();
    }
}
