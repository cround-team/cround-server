package croundteam.cround.member.presentation;

import croundteam.cround.board.application.dto.SearchBoardsResponses;
import croundteam.cround.creator.application.dto.SearchCreatorResponses;
import croundteam.cround.member.application.MemberService;
import croundteam.cround.member.application.dto.EmailValidationRequest;
import croundteam.cround.member.application.dto.MemberSaveRequest;
import croundteam.cround.member.application.dto.MemberUpdateRequest;
import croundteam.cround.member.application.dto.NicknameValidationRequest;
import croundteam.cround.shortform.application.dto.SearchShortFormResponses;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.search.BaseSearchCondition;
import croundteam.cround.support.vo.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> join(
            @RequestBody @Valid final MemberSaveRequest memberSaveRequest
    ) {
        Long memberId = memberService.saveMember(memberSaveRequest);
        return ResponseEntity.created(URI.create("/api/members/" + memberId)).build();
    }

    @GetMapping("/me/shorts/bookmarks")
    public ResponseEntity<SearchShortFormResponses> findShortFormOwnBookmarks(
            @Login LoginMember loginMember,
            BaseSearchCondition searchCondition
    ) {
        SearchShortFormResponses searchShortFormResponses = memberService.findShortFormOwnBookmarks(loginMember, searchCondition);
        return ResponseEntity.ok(searchShortFormResponses);
    }

    @GetMapping("/me/boards/bookmarks")
    public ResponseEntity<SearchBoardsResponses> findBoardOwnBookmarks(
            @Login LoginMember loginMember,
            BaseSearchCondition searchCondition
    ) {
        SearchBoardsResponses searchBoardsResponses = memberService.findBoardsOwnBookmarks(loginMember, searchCondition);
        return ResponseEntity.ok(searchBoardsResponses);
    }

    @GetMapping("/me/creators/followings")
    public ResponseEntity<SearchCreatorResponses> findCreatorOwnFollowings(
            @Login LoginMember loginMember,
            BaseSearchCondition searchCondition
    ) {
        SearchCreatorResponses searchCreatorResponses = memberService.findCreatorOwnFollowings(loginMember, searchCondition);
        return ResponseEntity.ok(searchCreatorResponses);
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMember(
            @RequestBody @Valid final MemberUpdateRequest memberUpdateRequest,
            @Login final LoginMember loginMember
    ) {
        memberService.updateMember(memberUpdateRequest, loginMember);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validations/email")
    public ResponseEntity<Void> validateEmail(
            @RequestBody @Valid final EmailValidationRequest emailValidationRequest
    ) {
        memberService.validateDuplicateEmail(emailValidationRequest.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validations/nickname")
    public ResponseEntity<Void> validateNickname(
            @RequestBody @Valid final NicknameValidationRequest nicknameValidationRequest
    ) {
        memberService.validateDuplicateNickname(nicknameValidationRequest.getNickname());
        return ResponseEntity.ok().build();
    }
}
