package croundteam.cround.member.presentation;

import croundteam.cround.member.application.dto.LoginSuccessResponse;
import croundteam.cround.member.application.dto.TokenResponse;
import croundteam.cround.member.application.AuthService;
import croundteam.cround.member.application.dto.LoginResponse;
import croundteam.cround.member.application.dto.MemberLoginRequest;
import croundteam.cround.support.CookieUtils;
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
        LoginSuccessResponse successResponse = authService.login(memberLoginRequest);
        ResponseCookie responseCookie = createResponseCookie(successResponse);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(LoginResponse.create(successResponse));
    }

    @GetMapping("/auth/{provider}/login")
    public ResponseEntity<LoginResponse> loginByOAuth(
            @PathVariable final String provider,
            @RequestParam final String code) {
        LoginSuccessResponse successResponse = authService.loginByOAuth(provider, code);
        ResponseCookie responseCookie = createResponseCookie(successResponse);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(LoginResponse.create(successResponse));
    }

    private static ResponseCookie createResponseCookie(LoginSuccessResponse loginSuccessResponse) {
        return CookieUtils.create(loginSuccessResponse.extractByRefreshToken());
    }
}
