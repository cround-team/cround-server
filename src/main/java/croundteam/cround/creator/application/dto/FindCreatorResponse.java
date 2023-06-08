package croundteam.cround.creator.application.dto;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindCreatorResponse {

    private String creatorNickname;
    private String description;
    private String profileImage;
    private String platformHeadTheme;
    private String platformHeadType;
    private String platformUrl;
    private List<String> tags;
    private List<PlatformType> activityPlatforms;
    private String avgRating;
    private int followersCount;
    private boolean isFollowed;

    public FindCreatorResponse(Creator creator, Member member) {
        this.profileImage = creator.getProfileImage();
        this.creatorNickname = creator.getNickname();
        this.platformHeadTheme = creator.getPlatformTheme();
        this.activityPlatforms = creator.getActivityPlatforms();
        this.avgRating = String.format("%.1f", creator.getAvgRating()); // 리뷰 평점
        this.followersCount = creator.getFollowersCount();
        this.platformHeadType = creator.getPlatformType();
        this.isFollowed = creator.isFollowedBy(member);
        this.description = creator.getDescription();
        this.tags = creator.getTags();
        this.platformUrl = creator.getPlatformUrl();
    }
}
