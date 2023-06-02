package croundteam.cround.security.token;

import croundteam.cround.security.CustomUserDetailsService;
import croundteam.cround.security.token.support.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static croundteam.cround.security.token.support.TokenProvider.AUTHORIZATION;
import static croundteam.cround.security.token.support.TokenProvider.BEARER;

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
                if (tokenProvider.validateToken(jwt)) {
                    String userEmail = tokenProvider.getUserEmailFromToken(jwt);

                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else { // 토큰 만료
                    Cookie[] cookies = request.getCookies();

                    String userEmailByRefreshToken = null;
                    for (Cookie cookie : cookies) {
                        if(cookie.getName().equals("refreshToken")) {
                            String refreshToken = cookie.getValue();

                            if(tokenProvider.validateToken(refreshToken)) {
                                userEmailByRefreshToken = tokenProvider.getUserEmailFromToken(refreshToken);
                                jwt = tokenProvider.createToken(userEmailByRefreshToken, "ACCESS");

                                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmailByRefreshToken);
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            } else {
                                System.out.println("토큰이 유효하지 않습니다.");
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                logger.error("message = " + ex.getMessage(), ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
