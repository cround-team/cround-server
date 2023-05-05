package croundteam.cround.auth;

import croundteam.cround.auth.application.CustomUserDetailsService;
import croundteam.cround.auth.support.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static croundteam.cround.auth.support.TokenProvider.AUTHORIZATION;
import static croundteam.cround.auth.support.TokenProvider.BEARER;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService custmUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = getJwtTokenFromRequest(request);

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            String userEmail = tokenProvider.getUserEmailFromToken(jwt);



        }


    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
