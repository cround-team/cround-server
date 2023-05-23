package croundteam.cround.shorts.domain;

import croundteam.cround.board.domain.Content;
import croundteam.cround.board.domain.Title;
import croundteam.cround.board.domain.bookmark.BoardBookmark;
import croundteam.cround.board.domain.like.BoardLike;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.shorts.domain.bookmark.ShortsBookmark;
import croundteam.cround.shorts.domain.like.ShortsLike;
import lombok.AccessLevel;
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
    @Column(nullable = false)
    private PlatformType platformType;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Embedded
    private ShortForm shortForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shorts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShortsLike> boardLikes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shorts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ShortsBookmark> boardBookmarks = new ArrayList<>();
}
