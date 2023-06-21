package croundteam.cround.shortform.domain;

import croundteam.cround.board.domain.Content;
import croundteam.cround.board.domain.Title;
import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "short_form")
public class ShortForm extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shorts_id")
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    @Embedded
    private ThumbnailImage thumbnailUrl;

    @Embedded
    private ShortFormUrl shortFormUrl;

    @Column(columnDefinition = "bigint default 0", nullable = false)
    private long visit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @Embedded
    private ShortFormLikes shortFormLikes;

    @Embedded
    private ShortFormBookmarks shortsBookmarks;

    @Builder
    public ShortForm(Title title, Content content, PlatformType platformType,
                     ThumbnailImage thumbnailUrl, ShortFormUrl shortFormUrl, Creator creator) {
        this.platformType = platformType;
        this.title = title;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.shortFormUrl = shortFormUrl;
        this.creator = creator;
    }

    public void addCreator(Creator creator) {
        this.creator = creator;
    }

    public void addThumbnailImage(String thumbnailImage) {
        this.thumbnailUrl = ThumbnailImage.create(thumbnailImage);
    }

    public void bookmark(Member member) {
        shortsBookmarks.addBookmark(this, member);
    }

    public void unbookmark(Member member) {
        shortsBookmarks.removeBookmark(this, member);
    }

    public void like(Member member) {
        shortFormLikes.addLike(this, member);
    }

    public void unlike(Member member) {
        shortFormLikes.removeLike(this, member);
    }

    public boolean isLikedBy(Member member) {
        if(Objects.isNull(member)) {
            return false;
        }
        return shortFormLikes.isLikedBy(this, member);
    }

    public boolean isBookmarkedBy(Member member) {
        if(Objects.isNull(member)) {
            return false;
        }
        return shortsBookmarks.isBookmarkedBy(this, member);
    }

    public boolean isAuthoredBy(Creator creator) {
        if(Objects.isNull(creator)) {
            return false;
        }
        return this.creator.equals(creator);
    }

    public void increaseVisit() {
        visit++;
    }

    public int getShortFormBookmarks() {
        return shortsBookmarks.getShortFormBookmarks();
    }

    public int getShortFormLikes() {
        return shortFormLikes.getLikes();
    }

    public String getThumbnailImage() {
        return thumbnailUrl.getThumbnailImage();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public String getPlatformType() {
        return platformType.getType();
    }

    public String getCreatorNickname() {
        return creator.getNickname();
    }

    public String getProfileImage() {
        return creator.getProfileImage();
    }

    public String getShortFormUrl() {
        return shortFormUrl.getShortFormUrl();
    }

}
