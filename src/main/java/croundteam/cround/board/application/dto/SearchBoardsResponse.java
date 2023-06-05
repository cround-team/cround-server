package croundteam.cround.board.application.dto;

import croundteam.cround.board.domain.Board;
import croundteam.cround.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchBoardsResponse {

    private Long boardId;
    private String title;
    private String content;
    private String platformType;
    private String profileImage;
    private String author;
    private int likesCount;
    private int bookmarksCount;
    private boolean isLiked;
    private boolean isBookmarked;

    @Builder
    public SearchBoardsResponse(Long boardId, String title, String content,
                                String platformType, String profileImage, String author,
                                int likesCount, int bookmarksCount, boolean isLiked, boolean isBookmarked) {
        this.boardId = boardId;
        this.platformType = platformType;
        this.title = title;
        this.content = content;
        this.profileImage = profileImage;
        this.author = author;
        this.likesCount = likesCount;
        this.bookmarksCount = bookmarksCount;
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
    }

    public static SearchBoardsResponse from(Board board, Member member) {
        return SearchBoardsResponse.builder()
                .boardId(board.getId())
                .platformType(board.getPlatformType())
                .title(board.getTitle())
                .content(board.getContent())
                .profileImage(board.getProfileImage())
                .author(board.getCreatorNickname())
                .likesCount(board.getBoardLikes())
                .bookmarksCount(board.getBoardBookmarks())
                .isLiked(board.isLikedBy(member))
                .isBookmarked(board.isBookmarkedBy(member))
                .build();
    }
}
