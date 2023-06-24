package croundteam.cround.creator.domain;

import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.creator.application.dto.CreatorUpdateRequest;
import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.creator.domain.tag.CreatorTag;
import croundteam.cround.creator.domain.tag.CreatorTags;
import croundteam.cround.creator.domain.tag.Tags;
import croundteam.cround.follow.domain.Follow;
import croundteam.cround.follow.domain.Followers;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.Nickname;
import croundteam.cround.review.domain.Review;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static croundteam.cround.creator.domain.ActivityPlatforms.castToActivityPlatforms;
import static croundteam.cround.creator.domain.tag.Tags.castToTags;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(name = "creator_member_unique", columnNames = "member_id"),
        indexes = @Index(name = "idx_creator_nickname", columnList = "nickname", unique = true))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Creator extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long id;

    @Embedded
    private Nickname nickname;

    @Embedded
    private Description description;

    @Column(name = "review_count")
    private int reviewCount;

    @Column(name = "total_rating")
    private int totalRating;

    @Column(name = "avg_rating")
    private double avgRating;

    @Embedded
    private Platform platform;

    @Embedded
    private ProfileImage profileImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_creator_to_member"))
    private Member member;

    @Embedded
    private CreatorTags creatorTags;

    @Embedded
    private ActivityPlatforms activityPlatforms;

    @Embedded
    private Followers followers;

    @Builder
    private Creator(Nickname nickname, Description description, Platform platform, ProfileImage profileImage,
                    Member member, Tags tags, ActivityPlatforms activityPlatforms) {
        this.nickname = nickname;
        this.description = description;
        this.platform = platform;
        this.profileImage = profileImage;
        this.member = member;
        this.creatorTags = castCreatorTagsFromTags(tags);
        this.activityPlatforms = activityPlatforms;
    }

    private CreatorTags castCreatorTagsFromTags(Tags tags) {
        return CreatorTags.create(this, tags);
    }

    public void addReview(Review review) {
        reviewCount += 1;
        totalRating += review.getRating();
        avgRating = (double) totalRating / reviewCount;
    }

    public void addMember(Member member) {
        member.updateCreatorType();
        this.member = member;
    }

    public void update(CreatorUpdateRequest requestCreator, Member member, String profileImage) {
        this.nickname = Nickname.create(requestCreator.getNickname());
        this.description = Description.create(requestCreator.getDescription());
        this.platform = requestCreator.getPlatform();
        this.member = member;
        this.creatorTags = CreatorTags.create(this, castToTags(requestCreator.getTags()));
        this.activityPlatforms = castToActivityPlatforms(requestCreator.getActivityPlatforms());
        this.profileImage = ProfileImage.create(profileImage);
    }

    public void addTags(List<CreatorTag> creatorTags) {
        this.creatorTags = CreatorTags.create(creatorTags);
    }

    public void addProfileImage(String profileImage) {
        this.profileImage = ProfileImage.create(profileImage);
    }

    public void addFollow(Follow follow) {
        followers.add(follow);
    }

    public void removeFollow(Follow follow) {
        followers.remove(follow);
    }

    public int getFollowersCount() {
        return followers.getFollowersCount();
    }

    public boolean isFollowedBy(Member member) {
        if(Objects.isNull(member)) {
            return false;
        }
        return followers.isFollowedBy(this, member);
    }

    public boolean isOwnedBy(Member member) {
        if(Objects.isNull(member)) {
            return false;
        }
        return this.member.equals(member);
    }

    public String getNickname() {
        return nickname.getName();
    }

    public String getPlatformType() {
        return platform.getPlatformType();
    }

    public String getPlatformTheme() {
        return platform.getPlatformTheme();
    }

    public String getDescription() {
        return description.getDescription();
    }

    public String getProfileImage() {
        return profileImage.getProfileImage();
    }

    public List<PlatformType> getActivityPlatforms() {
        return activityPlatforms.getPlatformTypes();
    }

    public String getPlatformUrl() {
        return platform.getPlatformUrl();
    }

    public List<String> getTags() {
        return creatorTags.castTagsFromCreatorTags();
    }
}
