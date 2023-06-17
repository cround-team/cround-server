package croundteam.cround.creator.presentation;

import croundteam.cround.board.application.dto.SearchBoardsResponses;
import croundteam.cround.creator.application.CreatorService;
import croundteam.cround.creator.application.dto.CreatorSaveRequest;
import croundteam.cround.creator.application.dto.FindCreatorResponse;
import croundteam.cround.creator.application.dto.FindHomeCreators;
import croundteam.cround.creator.application.dto.SearchCreatorResponses;
import croundteam.cround.member.application.dto.NicknameValidationRequest;
import croundteam.cround.shortform.application.dto.SearchShortFormResponses;
import croundteam.cround.support.annotation.Authenticated;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.search.BaseSearchCondition;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@Slf4j
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @PostMapping(consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createCreator(
            @RequestPart(required = false, value = "profileImage") MultipartFile file,
            @Login LoginMember loginMember,
            @RequestPart CreatorSaveRequest creatorSaveRequest
    ) {
        Long creatorId = creatorService.createCreator(file, loginMember, creatorSaveRequest);
        return ResponseEntity.created(URI.create("/api/creators/" + creatorId)).build();
    }

    @GetMapping
    public ResponseEntity<SearchCreatorResponses> searchCreators(SearchCondition searchCondition, Pageable pageable) {
        return ResponseEntity.ok(creatorService.searchCreatorsByCondition(searchCondition, pageable));
    }

    @GetMapping("/{creatorId}")
    public ResponseEntity<FindCreatorResponse> findOne(@PathVariable Long creatorId, @Authenticated AppUser appUser) {
        return ResponseEntity.ok(creatorService.findOne(appUser, creatorId));
    }

    @GetMapping("/home")
    public ResponseEntity<FindHomeCreators> findHomeCreators(
            @RequestParam(name = "size", defaultValue = "4") int size,
            @Authenticated AppUser appUser
    ) {
        FindHomeCreators findHomeCreators = creatorService.findHomeCreators(size, appUser);

        return ResponseEntity.ok(findHomeCreators);
    }

    @GetMapping("/{creatorId}/shorts")
    public ResponseEntity<SearchShortFormResponses> findShortsByCreator(
            @PathVariable Long creatorId,
            @Authenticated AppUser appUser,
            BaseSearchCondition searchCondition
    ) {
        SearchShortFormResponses searchShortFormResponses = creatorService.findShortsByCreator(creatorId, appUser, searchCondition);
        return ResponseEntity.ok(searchShortFormResponses);
    }

    @GetMapping("/{creatorId}/boards")
    public ResponseEntity<SearchBoardsResponses> findBoardsByCreator(
            @PathVariable Long creatorId,
            @Authenticated AppUser appUser,
            BaseSearchCondition searchCondition
    ) {
        SearchBoardsResponses searchBoardsResponses = creatorService.findBoardsByCreator(creatorId, appUser, searchCondition);
        return ResponseEntity.ok(searchBoardsResponses);
    }

    @PostMapping("/validations/nickname")
    public ResponseEntity<Void> validateNickname(
            @RequestBody @Valid final NicknameValidationRequest nicknameValidationRequest,
            @Login LoginMember loginMember
    ) {
        creatorService.findMemberByEmail(loginMember.getEmail());
        creatorService.validateDuplicateNickname(nicknameValidationRequest.getNickname());

        return ResponseEntity.ok().build();
    }

}
