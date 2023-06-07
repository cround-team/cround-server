package croundteam.cround.member.presentation;

import croundteam.cround.common.dto.TokenResponse;
import croundteam.cround.member.application.AuthService;
import croundteam.cround.member.application.dto.LoginResponse;
import croundteam.cround.member.application.dto.MemberLoginRequest;
import croundteam.cround.security.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final MemberLoginRequest memberLoginRequest) {
        TokenResponse tokenResponse = authService.login(memberLoginRequest);
        ResponseCookie responseCookie = createResponseCookie(tokenResponse);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(LoginResponse.create(tokenResponse.getAccessToken()));
    }

    @GetMapping("/auth/{provider}/login")
    public ResponseEntity<LoginResponse> loginByOAuth(
            @PathVariable final String provider,
            @RequestParam final String code) {
        TokenResponse tokenResponse = authService.loginByOAuth(provider, code);
        ResponseCookie responseCookie = createResponseCookie(tokenResponse);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(LoginResponse.create(tokenResponse.getAccessToken()));
    }

    private static ResponseCookie createResponseCookie(TokenResponse tokenResponse) {
        return CookieUtils.create(tokenResponse.excludeBearerInRefreshToken());
    }
}
