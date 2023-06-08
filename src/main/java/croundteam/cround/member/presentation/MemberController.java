package croundteam.cround.member.presentation;

import croundteam.cround.member.application.MemberService;
import croundteam.cround.member.application.dto.EmailValidationRequest;
import croundteam.cround.member.application.dto.MemberSaveRequest;
import croundteam.cround.member.application.dto.NicknameValidationRequest;
import croundteam.cround.support.annotation.Login;
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

    @GetMapping("/me/likes")
    public void findOwnLikes(@Login LoginMember loginMember) {
        memberService.findOwnLikes(loginMember);

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

    /**
     * /me/password
     * 비밀번호 재설정
     */
}
