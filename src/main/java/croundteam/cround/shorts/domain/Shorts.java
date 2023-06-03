package croundteam.cround.shorts.domain;

import croundteam.cround.board.domain.Content;
import croundteam.cround.board.domain.Title;
import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.Member;
import croundteam.cround.shorts.service.dto.ShortsSaveRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shorts_class")
public class Shorts extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shorts_id")
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Embedded
    private PlatformType platformType;

    @Embedded
    private ThumbnailUrl thumbnailUrl;

    @Embedded
    private ShortsUrl shortsUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @Embedded
    private ShortsLikes shortsLikes;

    @Embedded
    private ShortsBookmarks shortsBookmarks;

    @Builder
    public Shorts(PlatformType platformType, Title title, Content content,
                  ThumbnailUrl thumbnailUrl, ShortsUrl shortsUrl, Creator creator) {
        this.platformType = platformType;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.shortsUrl = shortsUrl;
        this.creator = creator;
    }

    public static Shorts of(Creator creator, ShortsSaveRequest shortsSaveRequest) {
        return Shorts.builder()
                .title(Title.create(shortsSaveRequest.getTitle()))
                .content(Content.create(shortsSaveRequest.getContent()))
                .platformType(PlatformType.create(shortsSaveRequest.getPlatformType()))
                .thumbnailUrl(ThumbnailUrl.create(shortsSaveRequest.getShortsUrl()))
                .creator(creator)
                .build();
    }

    public void bookmark(Member member) {
        shortsBookmarks.addBookmark(this, member);
    }

    public void unbookmark(Member member) {
        shortsBookmarks.removeBookmark(this, member);
    }

    public void like(Member member) {
        shortsLikes.addLike(this, member);
    }

    public void unlike(Member member) {
        shortsLikes.removeLike(this, member);
    }

    public int getShortsBookmarks() {
        return shortsBookmarks.getShortsBookmarks();
    }

    public int getShortsLikes() {
        return shortsLikes.getShortsLikes();
    }

    public String getThumbnailUrl() {
        return thumbnailUrl.getUrl();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public String getPlatformType() {
        return platformType.getPlatformName();
    }

    public String getCreatorActivityName() {
        return creator.getActivityName();
    }

    public boolean isLikedBy(Member member) {
        if(Objects.isNull(member)) {
            return false;
        }
        return shortsLikes.isLikedBy(this, member);
    }

    public boolean isBookmarkedBy(Member member) {
        if(Objects.isNull(member)) {
            return false;
        }
        return shortsBookmarks.isBookmarkedBy(member);
    }

    public String getProfileImage() {
        return creator.getProfileImage();
    }

    public String getShortsUrl() {
        return shortsUrl.getShortsUrl();
    }
}
