package croundteam.cround.creator.domain;

import croundteam.cround.common.domain.BaseTimeEntity;
import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.creator.domain.tag.CreatorTag;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.follow.Follow;
import croundteam.cround.member.domain.follow.Followers;
import croundteam.cround.tag.domain.Tags;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(
        name = "creator_member_unique",
        columnNames="member_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Creator extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long id;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_creator_to_member"))
    private Member member;

    @Embedded
    private Platform platform;

    @Embedded
    private Followers followers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.ALL)
    private List<CreatorTag> creatorTags = new ArrayList<>();

    @Builder
    private Creator(String profileImage, Member member, Platform platform, Tags creatorTags) {
        this.profileImage = profileImage;
        this.member = member;
        this.platform = platform;
        this.creatorTags = castTagsToCreatorTags(creatorTags);
    }

    public static Creator of(String profileImage, Member member, Platform platform, Tags creatorTags) {
        return Creator.builder()
                .profileImage(profileImage)
                .member(member)
                .platform(platform)
                .creatorTags(creatorTags)
                .build();
    }

    public void addFollowers(Follow follow) {
        getFollowers().add(follow);
    }

    public List<Follow> getFollowers() {
        return followers.getFollowers();
    }

    private List<CreatorTag> castTagsToCreatorTags(Tags tags) {
        return tags.toList().stream()
                .map(tag -> CreatorTag.of(this, tag))
                .collect(Collectors.toList());
    }
}
