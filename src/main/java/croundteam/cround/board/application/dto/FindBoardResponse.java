package croundteam.cround.board.application.dto;

import croundteam.cround.board.domain.Board;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindBoardResponse {

    private Long boardId;
    private String title;
    private String content;
    private String author;
    private String profileImage;
    private String platformType;
    private int likesCount;
    private int bookmarksCount;
    private boolean isLiked;
    private boolean isBookmarked;
    private boolean isAuthored;

    @Builder
    public FindBoardResponse(Long boardId, String title, String content,
                             String author, String profileImage, String platformType,
                             int likesCount, int bookmarksCount, boolean isLiked, boolean isBookmarked, boolean isAuthored) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.profileImage = profileImage;
        this.platformType = platformType;
        this.likesCount = likesCount;
        this.bookmarksCount = bookmarksCount;
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
        this.isAuthored = isAuthored;
    }

    public static FindBoardResponse from(Board board, Member member, Creator creator) {
        return FindBoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getCreatorNickname())
                .profileImage(board.getProfileImage())
                .platformType(board.getPlatformType())
                .likesCount(board.getBoardLikes())
                .bookmarksCount(board.getBoardBookmarks())
                .isLiked(board.isLikedBy(member))
                .isBookmarked(board.isBookmarkedBy(member))
                .isAuthored(board.isAuthoredBy(creator))
                .build();
    }
}
