package croundteam.cround.shortform.presentation;

import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.security.support.AppUser;
import croundteam.cround.security.support.Authenticated;
import croundteam.cround.security.support.Login;
import croundteam.cround.security.support.LoginMember;
import croundteam.cround.shortform.application.ShortFormService;
import croundteam.cround.shortform.application.dto.FindShortFormResponse;
import croundteam.cround.shortform.application.dto.SearchShortFormResponses;
import croundteam.cround.shortform.application.dto.ShortFormSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shorts")
public class ShortFormController {

    private final ShortFormService shortFormService;

    @PostMapping
    public ResponseEntity<Void> saveShortForm(
            @Login LoginMember member,
            @RequestBody @Valid ShortFormSaveRequest shortFormSaveRequest) {
        Long shortFormId = shortFormService.saveShortForm(member, shortFormSaveRequest);

        return ResponseEntity.created(URI.create("/api/shorts/" + shortFormId)).build();
    }

    @GetMapping
    public ResponseEntity<SearchShortFormResponses> searchShortForms(
            SearchCondition searchCondition,
            Pageable pageable,
            @Authenticated AppUser appUser
    ) {
        SearchShortFormResponses searchShortFormResponses = shortFormService.searchShortForm(searchCondition, pageable, appUser);
        return ResponseEntity.ok(searchShortFormResponses);
    }

    @GetMapping("/{shortsId}")
    public ResponseEntity<FindShortFormResponse> findOne(@PathVariable Long shortsId, @Authenticated AppUser appUser) {
        return ResponseEntity.ok(shortFormService.findOne(shortsId, appUser));
    }
}
