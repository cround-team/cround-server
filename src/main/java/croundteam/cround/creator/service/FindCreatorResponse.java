package croundteam.cround.creator.service;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.creator.domain.tag.CreatorTags;
import croundteam.cround.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindCreatorResponse {

    private String profileImage;
    private String platformActivityName;
    private String platformHeadTheme;
    private List<PlatformType> platformTypes;
    private int reviewsCount;
    private int followersCount;
    private String platformHeadType;
    private boolean isFollowed;
    private String description;
    private List<String> tags;
    private String platformUrl;

    public FindCreatorResponse(Creator creator, Member member, CreatorTags creatorTags) {
         List<String> tags = creatorTags.castTagsFromCreatorTags();

        this.profileImage = creator.getProfileImage();
        this.platformActivityName = creator.getActivityName();
        this.platformHeadTheme = creator.getPlatformTheme();
        this.platformTypes = creator.getActivityPlatforms();
        this.reviewsCount = 5; // 리뷰 평점
        this.followersCount = creator.getFollowersCount();
        this.platformHeadType = creator.getPlatformType();
        this.isFollowed = creator.isFollowedBy(member);
        this.description = creator.getDescription();
        this.tags = tags;
        this.platformUrl = creator.getPlatformUrl();
    }
}
