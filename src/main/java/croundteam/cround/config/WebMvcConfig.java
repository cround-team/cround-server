package croundteam.cround.config;

import croundteam.cround.support.annotation.AuthenticatedArgumentResolver;
import croundteam.cround.support.TokenProvider;
import croundteam.cround.support.annotation.LoginMemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private final TokenProvider tokenProvider;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedOrigins("http://localhost:3000")
                .exposedHeaders(HttpHeaders.LOCATION, HttpHeaders.SET_COOKIE);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver());
        resolvers.add(authenticatedArgumentResolver());
    }

    @Bean
    public LoginMemberArgumentResolver loginMemberArgumentResolver() {
        return new LoginMemberArgumentResolver(tokenProvider);
    }

    @Bean
    public AuthenticatedArgumentResolver authenticatedArgumentResolver() {
        return new AuthenticatedArgumentResolver(tokenProvider);
    }
}
