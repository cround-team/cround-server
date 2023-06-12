package croundteam.cround.support.search;

import lombok.*;

import static croundteam.cround.common.fixtures.ConstantFixtures.DEFAULT_PAGE_SIZE;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class BaseSearchCondition {

    private Long cursorId;
    private int size = DEFAULT_PAGE_SIZE;

}
