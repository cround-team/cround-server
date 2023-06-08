package croundteam.cround.shortform.application.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.shortform.domain.ShortForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchShortFormResponse {

    private Long shortsId;
    private String title;
    private String thumbnailUrl;
    private String platformType;
    private String profileImage;
    private String author;
    private int likesCount;
    private int bookmarksCount;
    private boolean isLiked;
    private boolean isBookmarked;

    @Builder
    public SearchShortFormResponse(Long shortsId, String thumbnailUrl, String title, String platformType,
                                   String profileImage, String author,
                                   int likesCount, int bookmarksCount, boolean isLiked, boolean isBookmarked) {
        this.shortsId = shortsId;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.platformType = platformType;
        this.profileImage = profileImage;
        this.author = author;
        this.likesCount = likesCount;
        this.bookmarksCount = bookmarksCount;
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
    }

    public static SearchShortFormResponse from(ShortForm shorts, Member member) {
        return SearchShortFormResponse.builder()
                .shortsId(shorts.getId())
                .thumbnailUrl(shorts.getThumbnailImage())
                .title(shorts.getTitle())
                .platformType(shorts.getPlatformType())
                .profileImage(shorts.getProfileImage())
                .author(shorts.getCreatorNickname())
                .likesCount(shorts.getShortFormLikes())
                .bookmarksCount(shorts.getBookmarkCount())
                .isLiked(shorts.isLikedBy(member))
                .isBookmarked(shorts.isBookmarkedBy(member))
                .build();
    }
}
