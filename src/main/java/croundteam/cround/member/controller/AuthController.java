package croundteam.cround.member.controller;

import croundteam.cround.member.service.AuthService;
import croundteam.cround.common.dto.TokenResponse;
import croundteam.cround.member.dto.MemberLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final MemberLoginRequest memberLoginRequest) {
        TokenResponse tokenResponse = authService.login(memberLoginRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @GetMapping("/auth/{provider}/login")
    public ResponseEntity<TokenResponse> loginByOAuth(@PathVariable String provider, @RequestParam String code) {
        TokenResponse tokenResponse = authService.loginByOAuth(provider, code);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
