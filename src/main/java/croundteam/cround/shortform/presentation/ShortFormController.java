package croundteam.cround.shortform.presentation;

import croundteam.cround.shortform.application.ShortFormService;
import croundteam.cround.shortform.application.dto.*;
import croundteam.cround.support.annotation.Authenticated;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.search.SearchCondition;
import croundteam.cround.support.vo.AppUser;
import croundteam.cround.support.vo.LoginMember;
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
    public ResponseEntity<FindPopularShortForms> findPopularShortForms(
            @RequestParam(name = "size", defaultValue = "3") int size,
            @Authenticated AppUser appUser
    ) {
       return ResponseEntity.ok(shortFormService.findPopularShortForms(size, appUser));
    }

    @PatchMapping("/{shortsId}")
    public ResponseEntity<Void> updateShortForm(
            @PathVariable final Long shortsId,
            @RequestPart final ShortFormUpdateRequest shortFormUpdateRequest,
            @Login final LoginMember loginMember,
            @RequestPart(required = false, value = "thumbnailImage") MultipartFile file
    ) {
        shortFormService.updateShortFrom(shortsId, shortFormUpdateRequest, loginMember, file);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{shortsId}")
    public ResponseEntity<Void> deleteShortForm(
            @PathVariable final Long shortsId,
            @Login final LoginMember loginMember) {
        shortFormService.deleteShortForm(shortsId, loginMember);

        return ResponseEntity.noContent().build();
    }
}
