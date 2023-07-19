package croundteam.cround.board.domain;

import croundteam.cround.board.application.dto.BoardSaveRequest;
import croundteam.cround.board.application.dto.BoardUpdateRequest;
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
@Table(name = "board")
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Creator creator;

    @Embedded
    private BoardLikes boardLikes;

    @Embedded
    private BoardBookmarks boardBookmarks;

    @Builder
    public Board(Title title, Content content, PlatformType platformType, Creator creator) {
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

    public void update(BoardUpdateRequest boardUpdateRequest) {
        this.title = Title.create(boardUpdateRequest.getTitle());
        this.content = Content.create(boardUpdateRequest.getContent());
        this.platformType = PlatformType.create(boardUpdateRequest.getPlatformType());
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

    public boolean isLikedBy(Member member) {
        if(Objects.isNull(member)) {
            return false;
        }
        return boardLikes.isLikedBy(this, member);
    }

    public boolean isBookmarkedBy(Member member) {
        if(Objects.isNull(member)) {
            return false;
        }
        return boardBookmarks.isBookmarkedBy(this, member);
    }

    public boolean isAuthoredBy(Creator creator) {
        if(Objects.isNull(creator)) {
            return false;
        }
        return this.creator.equals(creator);
    }

    public int getBoardLikes() {
        return boardLikes.getLikeCount();
    }

    public int getBoardBookmarks() {
        return boardBookmarks.getBookmarkCount();
    }

    public String getPlatformType() {
        return platformType.getType();
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getContent() {
        return content.getContent();
    }

    public String getProfileImage() {
        return creator.getProfileImage();
    }

    public String getCreatorNickname() {
        return creator.getNickname();
    }
}
