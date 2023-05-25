package croundteam.cround.creator.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCreatorResponses {

    private Page<SearchCreatorResponse> pages;

}
