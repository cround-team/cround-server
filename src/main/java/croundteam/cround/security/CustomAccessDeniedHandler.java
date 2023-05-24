package croundteam.cround.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import croundteam.cround.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        log.info("[AccessDeniedHandler] Invalid Authorization = {}", accessDeniedException.getClass().getName());

        Map<String, String> map = new HashMap<>();
        map.put("data", null);
        map.put("message", ErrorCode.INVALID_AUTHORIZATION.getMessage());
        String json = objectMapper.writeValueAsString(map);

        response.getWriter().write(json);
    }

}
