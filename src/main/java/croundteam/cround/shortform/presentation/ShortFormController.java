package croundteam.cround.shortform.presentation;

import croundteam.cround.shortform.application.dto.FindPopularShortForms;
import croundteam.cround.support.search.SearchCondition;
import croundteam.cround.support.vo.AppUser;
import croundteam.cround.support.annotation.Authenticated;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.vo.LoginMember;
import croundteam.cround.shortform.application.ShortFormService;
import croundteam.cround.shortform.application.dto.FindShortFormResponse;
import croundteam.cround.shortform.application.dto.SearchShortFormResponses;
import croundteam.cround.shortform.application.dto.ShortFormSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestPart(required = false, value = "thumbnailImage") MultipartFile file,
            @Login LoginMember loginMember,
            @RequestPart @Valid ShortFormSaveRequest shortFormSaveRequest) {
        Long shortFormId = shortFormService.saveShortForm(file, loginMember, shortFormSaveRequest);

        return ResponseEntity.created(URI.create("/api/shorts/" + shortFormId)).build();
    }

    @GetMapping
    public ResponseEntity<SearchShortFormResponses> searchShortForms(
            SearchCondition searchCondition, Pageable pageable,
            @Authenticated AppUser appUser
    ) {
        SearchShortFormResponses searchShortFormResponses = shortFormService.searchShortForm(searchCondition, pageable, appUser);
        return ResponseEntity.ok(searchShortFormResponses);
    }

    @GetMapping("/{shortsId}")
    public ResponseEntity<FindShortFormResponse> findOne(@PathVariable Long shortsId, @Authenticated AppUser appUser) {
        return ResponseEntity.ok(shortFormService.findOne(shortsId, appUser));
    }

    @GetMapping("/populars")
    public ResponseEntity<FindPopularShortForms> findPopularShorts(
            @RequestParam(name = "size", defaultValue = "3") int size,
            @Authenticated AppUser appUser
    ) {
       return ResponseEntity.ok(shortFormService.findPopularShortForm(size, appUser));
    }
}
