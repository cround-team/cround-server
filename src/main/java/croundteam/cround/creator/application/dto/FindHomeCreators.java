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
    List<SearchCreatorResponse> randomCreators = new ArrayList<>();

    public FindHomeCreators(List<Creator> latestCreators, List<Creator> interestCreators, List<Creator> randomCreators) {
        this.latestCreators = convertToSearchCreatorResponse(latestCreators);
        this.interestCreators = convertToSearchCreatorResponse(interestCreators);
        this.randomCreators = convertToSearchCreatorResponse(randomCreators);
    }

    private static List<SearchCreatorResponse> convertToSearchCreatorResponse(List<Creator> creators) {
        return creators.stream().map(SearchCreatorResponse::from).collect(toList());
    }
}
