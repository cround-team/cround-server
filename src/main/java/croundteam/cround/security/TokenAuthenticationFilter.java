package croundteam.cround.security;

import croundteam.cround.support.CookieUtils;
import croundteam.cround.support.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static croundteam.cround.support.TokenProvider.AUTHORIZATION;
import static croundteam.cround.support.TokenProvider.BEARER;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = getJwtTokenFromRequest(request);

        if(StringUtils.hasText(jwt)) {
            try {
                if (isValidateToken(jwt)) {
                    Authentication authentication = getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else { // 액세스 토큰 만료
                    String token = CookieUtils.extractTokenByCookies(request);

                    if(isValidateToken(token)) {
                        tokenProvider.setAuthorizationHeader(token, response);

                        Authentication authentication = getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception ex) {
                log.info("[Token Filter] {}", ex.getMessage(), ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String jwt) {
        String userEmail = tokenProvider.getSubject(jwt);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private boolean isValidateToken(String jwt) {
        return tokenProvider.validateToken(jwt);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
