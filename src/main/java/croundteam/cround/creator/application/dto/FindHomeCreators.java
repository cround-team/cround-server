package croundteam.cround.creator.application.dto;

import croundteam.cround.creator.domain.Creator;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
public class FindHomeCreators {

    List<SearchCreatorResponse> latestCreators = new ArrayList<>();
    List<SearchCreatorResponse> interestCreators = new ArrayList<>();

    public FindHomeCreators(List<Creator> latestCreators, List<Creator> interestCreators) {
        this.latestCreators = convertToSearchCreatorResponse(latestCreators);
        this.interestCreators = convertToSearchCreatorResponse(interestCreators);
    }

    private static List<SearchCreatorResponse> convertToSearchCreatorResponse(List<Creator> creators) {
        return creators.stream().map(SearchCreatorResponse::from).collect(toList());
    }
}
