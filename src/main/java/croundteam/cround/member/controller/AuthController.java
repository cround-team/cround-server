package croundteam.cround.member.controller;

import croundteam.cround.member.service.AuthService;
import croundteam.cround.common.dto.TokenResponse;
import croundteam.cround.member.dto.MemberLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final MemberLoginRequest memberLoginRequest) {
        TokenResponse tokenResponse = authService.login(memberLoginRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
