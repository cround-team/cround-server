package croundteam.cround.shortform.application.dto;

import croundteam.cround.board.domain.Content;
import croundteam.cround.board.domain.Title;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.domain.ShortFormUrl;
import croundteam.cround.shortform.domain.ThumbnailUrl;
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
    private String thumbnailUrl;

    public ShortForm toEntity() {
        return ShortForm.builder()
                .title(Title.create(title))
                .content(Content.create(content))
                .platformType(PlatformType.create(platformType))
                .thumbnailUrl(ThumbnailUrl.create(shortFormUrl))
                .shortFormUrl(ShortFormUrl.create(thumbnailUrl))
                .build();
    }
}
