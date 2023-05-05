package croundteam.cround.auth.presentation;

import croundteam.cround.auth.domain.AuthProvider;
import croundteam.cround.auth.service.AuthService;
import croundteam.cround.common.dto.TokenResponse;
import croundteam.cround.member.dto.MemberLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
