package croundteam.cround.shorts.domain;

import croundteam.cround.board.domain.Content;
import croundteam.cround.board.domain.Title;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.Member;
import croundteam.cround.shorts.dto.ShortsSaveRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shorts_class")
public class Shorts {

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
    private ShortForm shortForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @Embedded
    private ShortsLikes shortsLikes;

    @Embedded
    private ShortsBookmarks shortsBookmarks;

    @Builder
    public Shorts(PlatformType platformType, Title title, Content content, ShortForm shortForm, Creator creator) {
        this.platformType = platformType;
        this.title = title;
        this.content = content;
        this.shortForm = shortForm;
        this.creator = creator;
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

    public static Shorts of(Creator creator, ShortsSaveRequest shortsSaveRequest) {
        return Shorts.builder()
                .title(Title.from(shortsSaveRequest.getTitle()))
                .content(Content.from(shortsSaveRequest.getContent()))
                .platformType(PlatformType.from(shortsSaveRequest.getPlatformType()))
                .shortForm(ShortForm.from(shortsSaveRequest.getShortsUrl()))
                .creator(creator)
                .build();
    }

    public int getShortsBookmarks() {
        return shortsBookmarks.getShortsBookmarks();
    }

    public int getShortsLikes() {
        return shortsLikes.getShortsLikes();
    }
}
