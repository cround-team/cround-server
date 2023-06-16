package croundteam.cround.shortform.application.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.shortform.domain.ShortForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindShortFormResponse {

    private Long shortsId;
    private String title;
    private String content;
    private String author;
    private String platformType;
    private String profileImage;
    private String shortFormUrl;
    private int likesCount;
    private int bookmarksCount;
    private boolean isLiked;
    private boolean isBookmarked;
    private boolean isAuthored;

    @Builder
    public FindShortFormResponse(Long shortsId, String title, String content,
                                 String author, String platformType, String profileImage, String shortFormUrl,
                                 int likesCount, int bookmarksCount, boolean isLiked, boolean isBookmarked, boolean isAuthored) {
        this.shortsId = shortsId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.platformType = platformType;
        this.profileImage = profileImage;
        this.shortFormUrl = shortFormUrl;
        this.likesCount = likesCount;
        this.bookmarksCount = bookmarksCount;
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
        this.isAuthored = isAuthored;
    }

    public static FindShortFormResponse from(ShortForm shorts, Member member) {
        return FindShortFormResponse.builder()
                .shortsId(shorts.getId())
                .title(shorts.getTitle())
                .content(shorts.getContent())
                .author(shorts.getCreatorNickname())
                .platformType(shorts.getPlatformType())
                .profileImage(shorts.getProfileImage())
                .shortFormUrl(shorts.getShortFormUrl())
                .likesCount(shorts.getShortFormLikes())
                .bookmarksCount(shorts.getShortFormBookmarks())
                .isLiked(shorts.isLikedBy(member))
                .isBookmarked(shorts.isBookmarkedBy(member))
                .isAuthored(shorts.isAuthoredBy(member))
                .build();
    }
}
