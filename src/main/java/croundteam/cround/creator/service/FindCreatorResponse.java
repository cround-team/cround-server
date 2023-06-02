package croundteam.cround.creator.service;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.creator.domain.tag.CreatorTags;
import croundteam.cround.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 크리에이터 사진
 * 크리에이터 활동명
 * 크리에이터 대표 테마
 * 크리에이터 활동 플랫폼
 * 리뷰 점수
 * 팔로워 수
 * 크리에이터 대표 플랫폼
 * 사용자의 팔로워 상태
 * 크리에이터 설명
 * 크리에이터 태그
 */
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

    public FindCreatorResponse(Creator creator, Member member, CreatorTags creatorTags) {
         List<String> tags = creatorTags.castTagsFromCreatorTags();

        this.profileImage = creator.getProfileImage();
        this.platformActivityName = creator.getActivityName();
        this.platformHeadTheme = creator.getPlatformTheme();
        this.platformTypes = creator.getActivityPlatforms();
        this.reviewsCount = 5; // 리뷰 개수
        this.followersCount = creator.getFollowersCount();
        this.platformHeadType = creator.getPlatformType();
        this.isFollowed = creator.isFollowedBy(member);
        this.description = creator.getDescription();
        this.tags = tags;
    }
}
