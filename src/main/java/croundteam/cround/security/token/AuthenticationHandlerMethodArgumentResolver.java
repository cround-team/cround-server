package croundteam.cround.security.token;

import croundteam.cround.member.service.dto.LoginMember;
import croundteam.cround.security.token.support.JwtTokenExtractor;
import croundteam.cround.security.token.support.Login;
import croundteam.cround.security.token.support.TokenProvider;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static croundteam.cround.security.token.support.TokenProvider.AUTHORIZATION;

public class AuthenticationHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    public AuthenticationHandlerMethodArgumentResolver(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String authorization = request.getHeader(AUTHORIZATION);
        String token = JwtTokenExtractor.extract(authorization);

        String email = tokenProvider.getUserEmailFromToken(token);

        return new LoginMember(email);
    }
}
