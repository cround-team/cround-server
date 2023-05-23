package croundteam.cround.member.controller;

import croundteam.cround.member.dto.*;
import croundteam.cround.member.service.MemberService;
import croundteam.cround.member.service.dto.*;
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
    public ResponseEntity<Void> join(@RequestBody @Valid final MemberSaveRequest memberSaveRequest) {
        Long memberId = memberService.saveMember(memberSaveRequest);
        return ResponseEntity.created(URI.create("/api/members/" + memberId)).build();
    }

    @PostMapping("/validations/email")
    public ResponseEntity<Void> validateEmail(@RequestBody @Valid final EmailValidationRequest emailValidationRequest) {
        memberService.validateDuplicateEmail(emailValidationRequest.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validations/nickname")
    public ResponseEntity<Void> validateNickname(@RequestBody @Valid final NicknameValidationRequest nicknameValidationRequest) {
        memberService.validateDuplicateNickname(nicknameValidationRequest.getNickname());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/following")
    public ResponseEntity<FollowResponse> followCreator(@RequestBody FollowRequest followRequest) {
        FollowResponse followResponse = memberService.followCreator(followRequest);
        return ResponseEntity.ok(followResponse);
    }

    @DeleteMapping("/following")
    public ResponseEntity<FollowResponse> unfollowCreator(@RequestBody FollowRequest followRequest) {
        FollowResponse followResponse = memberService.unfollowCreator(followRequest);
        return ResponseEntity.ok(followResponse);
    }


    /**
     * /me/password
     * 비밀번호 재설정
     */
}
