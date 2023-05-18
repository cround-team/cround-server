package croundteam.cround.board.domain;

import croundteam.cround.board.dto.BoardSaveRequest;
import croundteam.cround.bookmark.domain.BoardBookmark;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.like.domain.BoardLike;
import croundteam.cround.member.domain.Member;
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
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private PlatformType platformType;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BoardLike> boardLikes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BoardBookmark> boardBookmarks = new ArrayList<>();

    @Builder
    public Board(PlatformType platformType, Title title, Content content, Creator creator) {
        this.platformType = platformType;
        this.title = title;
        this.content = content;
        this.creator = creator;
    }

    public void like(Member member) {
        BoardLike like = new BoardLike(this, member);
        boardLikes.add(like);
    }

    public void unlike(Member member) {
        BoardLike like = new BoardLike(this, member);
        boardLikes.remove(like);
    }

    public void bookmark(Member member) {
        BoardBookmark bookmark = new BoardBookmark(this, member);
        boardBookmarks.add(bookmark);
    }

    public void unbookmark(Member member) {
        BoardBookmark bookmark = new BoardBookmark(this, member);
        boardBookmarks.remove(bookmark);
    }

    public static Board of(Creator creator, BoardSaveRequest boardSaveRequest) {
        return Board.builder()
                .platformType(PlatformType.from(boardSaveRequest.getPlatformType()))
                .title(Title.from(boardSaveRequest.getTitle()))
                .content(Content.from(boardSaveRequest.getContent()))
                .creator(creator)
                .build();
    }

    public int getBoardLikes() {
        return boardLikes.size();
    }

    public int getBoardBookmarks() {
        return boardBookmarks.size();
    }

    public String getPlatformType() {
        return platformType.getPlatformName();
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getContent() {
        return content.getValue();
    }

    public String getAuthor() {
        return creator.getActivityName();
    }
}
