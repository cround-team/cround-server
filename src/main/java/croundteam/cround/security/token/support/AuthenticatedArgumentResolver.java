package croundteam.cround.security.token.support;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static croundteam.cround.security.token.support.TokenProvider.AUTHORIZATION;

public class AuthenticatedArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    public AuthenticatedArgumentResolver(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticated.class);
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
        String email = extractEmailBy(authorization);

        return new AppUser(email);
    }

    private String extractEmailBy(String authorization) {
        if(!StringUtils.hasText(authorization)) {
            return null;
        }
        String token = JwtTokenExtractor.extract(authorization);
        if(tokenProvider.validateToken(token)) {
            return tokenProvider.getSubject(token);
        }
        return null;
    }
}
