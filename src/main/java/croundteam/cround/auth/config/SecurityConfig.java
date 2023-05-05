package croundteam.cround.auth.config;

import croundteam.cround.auth.application.oauth.CustomOAuth2UserService;
import croundteam.cround.auth.application.oauth.OAuthSuccessHandler;
import croundteam.cround.auth.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/favicon.ico", "/error").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/members", "/api/members/login/token").permitAll()
                .anyRequest().authenticated();

        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuthSuccessHandler);

        http
                .logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");

//        http
//                .exceptionHandling()
//                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
//                .accessDeniedHandler(new JwtAccessDeniedHandler());

        http
                .addFilterBefore((tokenAuthenticationFilter()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
