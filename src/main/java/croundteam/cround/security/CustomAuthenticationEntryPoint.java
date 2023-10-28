package croundteam.cround.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import croundteam.cround.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(ContentType.APPLICATION_JSON.toString());

        log.info("[AuthenticationEntryPoint] Invalid Authentication = {}", authException.getClass().getName());

        Map<String, String> map = new HashMap<>();
        map.put("data", null);
        map.put("message", ErrorCode.INVALID_AUTHENTICATION.getMessage());
        String json = objectMapper.writeValueAsString(map);

        response.getWriter().write(json);
    }

}
