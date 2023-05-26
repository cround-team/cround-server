package croundteam.cround.board.domain;

import croundteam.cround.board.service.dto.BoardSaveRequest;
import croundteam.cround.common.domain.BaseTime;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @Embedded
    private PlatformType platformType;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Embedded
    private BoardLikes boardLikes;

    @Embedded
    private BoardBookmarks boardBookmarks;

    @Builder
    public Board(PlatformType platformType, Title title, Content content, Creator creator) {
        this.platformType = platformType;
        this.title = title;
        this.content = content;
        this.creator = creator;
    }

    public static Board of(Creator creator, BoardSaveRequest boardSaveRequest) {
        return Board.builder()
                .platformType(PlatformType.create(boardSaveRequest.getPlatformType()))
                .title(Title.create(boardSaveRequest.getTitle()))
                .content(Content.create(boardSaveRequest.getContent()))
                .creator(creator)
                .build();
    }

    public void like(Member member) {
        boardLikes.like(this, member);
    }

    public void unlike(Member member) {
        boardLikes.unlike(this, member);
    }

    public void bookmark(Member member) {
        boardBookmarks.bookmark(this, member);
    }

    public void unbookmark(Member member) {
        boardBookmarks.unbookmark(this, member);
    }

    public int getBoardLikes() {
        return boardLikes.getLikeCount();
    }

    public int getBoardBookmarks() {
        return boardBookmarks.getBookmarkCount();
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
