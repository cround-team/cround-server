package croundteam.cround.shorts.service.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.shorts.domain.Shorts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchShortsResponse {

    private Long shortsId;
    private String thumbnailUrl;
    private String title;
    private String platformType;
    private String profileImage;
    private String author;
    private int likesCount;
    private int bookmarksCount;
    private boolean isLiked;
    private boolean isBookmarked;

    @Builder
    public SearchShortsResponse(Long shortsId, String thumbnailUrl, String title, String platformType,
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

    public static SearchShortsResponse from(Shorts shorts, Member member) {
        return SearchShortsResponse.builder()
                .shortsId(shorts.getId())
                .thumbnailUrl(shorts.getThumbnailUrl())
                .title(shorts.getTitle())
                .platformType(shorts.getPlatformType())
                .profileImage(shorts.getProfileImage())
                .author(shorts.getCreatorActivityName())
                .likesCount(shorts.getShortsLikes())
                .bookmarksCount(shorts.getShortsBookmarks())
                .isLiked(shorts.isLikedBy(member))
                .isBookmarked(shorts.isBookmarkedBy(member))
                .build();
    }
}
