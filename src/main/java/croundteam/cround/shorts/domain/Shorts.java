package croundteam.cround.shorts.domain;

import croundteam.cround.board.domain.Content;
import croundteam.cround.board.domain.Title;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.shorts.domain.bookmark.ShortsBookmark;
import croundteam.cround.shorts.domain.like.ShortsLike;
import croundteam.cround.shorts.dto.ShortsSaveRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shorts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShortsLike> boardLikes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shorts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShortsBookmark> boardBookmarks = new ArrayList<>();

    @Builder
    public Shorts(PlatformType platformType, Title title, Content content, ShortForm shortForm, Creator creator) {
        this.platformType = platformType;
        this.title = title;
        this.content = content;
        this.shortForm = shortForm;
        this.creator = creator;
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
}
