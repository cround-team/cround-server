package croundteam.cround.creator.domain;

import croundteam.cround.board.domain.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Boards {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<Board> boards = new ArrayList<>();

    public Boards(List<Board> boards) {
        this.boards = boards;
    }

    public void add(Board board) {
        boards.add(board);
    }
}
