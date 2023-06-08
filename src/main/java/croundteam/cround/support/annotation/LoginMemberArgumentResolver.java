package croundteam.cround.support.annotation;

import croundteam.cround.support.TokenProvider;
import croundteam.cround.support.JwtTokenExtractor;
import croundteam.cround.support.vo.LoginMember;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    public LoginMemberArgumentResolver(TokenProvider tokenProvider) {
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
        String authorization = request.getHeader(TokenProvider.AUTHORIZATION);
        String token = JwtTokenExtractor.extract(authorization);
        String email = tokenProvider.getSubject(token);

        return new LoginMember(email);
    }
}
