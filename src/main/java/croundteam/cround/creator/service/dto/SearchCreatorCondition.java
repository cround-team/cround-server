package croundteam.cround.creator.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter
public class SearchCreatorCondition {
    private List<String> platforms;
    private String keyword;
}
