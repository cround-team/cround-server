package croundteam.cround.support.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static croundteam.cround.common.fixtures.ConstantFixtures.DEFAULT_PAGE_SIZE;

@NoArgsConstructor
@Getter @Setter
public class BaseSearchCondition {

    private long cursorId;
    private int size = DEFAULT_PAGE_SIZE;

    public BaseSearchCondition(long cursorId, int size) {
        this.cursorId = cursorId;
        this.size = size;
    }
}
