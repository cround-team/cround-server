package croundteam.cround.board.application.dto;

import croundteam.cround.board.domain.Board;
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

    @Builder
    public FindBoardResponse(Long boardId, String title, String content,
                             String author, String profileImage, String platformType) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.profileImage = profileImage;
        this.platformType = platformType;
    }

    public static FindBoardResponse from(Board board) {
        return FindBoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getCreatorNickname())
                .profileImage(board.getProfileImage())
                .platformType(board.getPlatformType())
                .build();
    }
}
