package croundteam.cround.config;

import croundteam.cround.security.CustomAccessDeniedHandler;
import croundteam.cround.security.CustomAuthenticationEntryPoint;
import croundteam.cround.security.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOriginPatterns(List.of("*"));
                    cors.setAllowedMethods(List.of("*"));
                    cors.setAllowedHeaders(List.of("*"));
                    cors.addExposedHeader("Authorization");
                    cors.addExposedHeader("Refresh_Token");
                    cors.setAllowCredentials(true);

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", cors);

                    return cors;
                });

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
                .antMatchers("/cround/health").permitAll()
                .antMatchers(
                        HttpMethod.GET, "/auth/kakao/login",
                        "/api/boards", "/api/boards/{boardId}", "/api/creators", "/api/creators/{creatorId}",
                        "/api/shorts", "/api/shorts/{shortsId}",
                        "/api/creators/{creatorId}/reviews")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login", "/api/members",
                        "/api/members/validations/email", "/api/members/validations/nickname").permitAll()
                .anyRequest().authenticated();

        http
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize");

//        http
//                .logout()
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/");

        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        http
                .addFilterBefore((tokenAuthenticationFilter()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
