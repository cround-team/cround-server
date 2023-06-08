package croundteam.cround.support;

import croundteam.cround.member.application.dto.TokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";

    public static final String ACCESS = "ACCESS";
    public static final String REFRESH = "REFRESH";

    public static final long ACCESS_TOKEN_EXPIRE = 2 * 60 * 60 * 1000L;       //   2 Hour
    public static final long REFRESH_TOKEN_EXPIRE = 7 * 24 * 60 * 60 * 1000L; // 168 Hour

    @Value("${security.jwt.secret}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public TokenResponse generateToken(String email) {
        return new TokenResponse(createToken(email, ACCESS), createToken(email, REFRESH));
    }

    public String createToken(String email, String type) {
        Date date = new Date();

        long time = type.equals(ACCESS) ? ACCESS_TOKEN_EXPIRE : REFRESH_TOKEN_EXPIRE;

        return BEARER + Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(date.getTime() + time))
                .setIssuedAt(date)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException ex) {
            log.error("[JWT Token] Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("[JWT Token] Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("[JWT Token] Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("[JWT Token] Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("[JWT Token] JWT claims string is empty.");
        }
        return false;
    }

    public String getSubject(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public void setAuthorizationHeader(String token, HttpServletResponse response) {
        String issueToken = createToken(getSubject(token), ACCESS);
        response.setHeader(AUTHORIZATION, issueToken);

        log.info("[JWT Token] issue Access Token = {}", issueToken);
    }
}
