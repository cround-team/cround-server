package croundteam.cround.creator.domain;

import croundteam.cround.board.domain.Board;
import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.creator.domain.tag.Tags;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.follow.Follow;
import croundteam.cround.member.domain.follow.Followers;
import croundteam.cround.shorts.domain.Shorts;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(
        name = "creator_member_unique",
        columnNames="member_id"),
        indexes = @Index(name = "idx_platform_activity_name", columnList = "platform_activity_name", unique = true))
// SELECT * FROM INFORMATION_SCHEMA.INDEXES where INDEX_NAME = 'IDX_PLATFORM_ACTIVITY_NAME';
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Creator extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Long id;

    @Embedded
    private Description description;

    @Column(name = "profile_image")
    private String profileImage;

    @Embedded
    private Platform platform;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_creator_to_member"))
    private Member member;

    @Embedded
    private Followers followers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<CreatorTag> creatorTags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<Shorts> shorts = new ArrayList<>();

    @Builder
    private Creator(String profileImage, Member member, Platform platform, Tags tags, Description description) {
        this.profileImage = profileImage;
        this.member = member;
        this.platform = platform;
        this.creatorTags = tags.castCreatorTagsFromTags(this);
        this.description = description;
    }

    public static Creator of(String profileImage, Member member, Platform platform, Tags tags) {
        return Creator.builder()
                .profileImage(profileImage)
                .member(member)
                .platform(platform)
                .tags(tags)
                .build();
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void addBoard(Board board) {
        boards.add(board);
    }

    public void addShorts(Shorts shorts) {
        this.shorts.add(shorts);
    }

    public void addFollow(Follow follow) {
        followers.add(follow);
    }

    public void removeFollow(Follow follow) {
        followers.remove(follow);
    }

    public String getActivityName() {
        return platform.getPlatformActivityName();
    }

    public Long getMemberId() {
        return member.getId();
    }

    public String getDescription() {
        return description.getDescription();
    }
}
