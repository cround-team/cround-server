package croundteam.cround.shorts.service.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.shorts.domain.Shorts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindShortsResponse {

    private Long shortsId;
    private String title;
    private String content;
    private String author;
    private String platformType;
    private String profileImage;
    private String shortsUrl;
    private int likesCount;
    private int bookmarksCount;
    private boolean isLiked;
    private boolean isBookmarked;

    @Builder
    public FindShortsResponse(Long shortsId, String title, String content,
                              String author, String platformType, String profileImage, String shortsUrl,
                              int likesCount, int bookmarksCount, boolean isLiked, boolean isBookmarked) {
        this.shortsId = shortsId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.platformType = platformType;
        this.profileImage = profileImage;
        this.shortsUrl = shortsUrl;
        this.likesCount = likesCount;
        this.bookmarksCount = bookmarksCount;
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
    }

    public static FindShortsResponse from(Shorts shorts, Member member) {
        return FindShortsResponse.builder()
                .shortsId(shorts.getId())
                .title(shorts.getTitle())
                .content(shorts.getContent())
                .author(shorts.getCreatorActivityName())
                .platformType(shorts.getPlatformType())
                .profileImage(shorts.getProfileImage())
                .shortsUrl(shorts.getShortsUrl())
                .likesCount(shorts.getShortsLikes())
                .bookmarksCount(shorts.getShortsBookmarks())
                .isLiked(shorts.isLikedBy(member))
                .isBookmarked(shorts.isBookmarkedBy(member))
                .build();
    }
}
