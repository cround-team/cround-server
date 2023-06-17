package croundteam.cround.shortform.application.dto;

import croundteam.cround.member.domain.Member;
import croundteam.cround.shortform.domain.ShortForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static croundteam.cround.shortform.application.dto.SearchShortFormResponse.from;
import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
public class FindPopularShortForms {

    private List<SearchShortFormResponse> popularLikeShortForms = new ArrayList<>();
    private List<SearchShortFormResponse> popularBookmarkShortForms = new ArrayList<>();

    public FindPopularShortForms(
            List<ShortForm> highestLikeShortForms,
            List<ShortForm> popularBookmarkShortForms,
            Member member
    ) {
        this.popularLikeShortForms = convertToSearchShortFormResponse(highestLikeShortForms, member);
        this.popularBookmarkShortForms = convertToSearchShortFormResponse(popularBookmarkShortForms, member);
    }

    private static List<SearchShortFormResponse> convertToSearchShortFormResponse(List<ShortForm> shortForms, Member member) {
        return shortForms.stream().map(shortForm -> from(shortForm, member)).collect(toList());
    }
}
