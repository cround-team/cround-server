package croundteam.cround.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/cround/health")
    public Map<String, String> health() {
        Map<String, String> map = new HashMap<>();
        map.put("status", "UP");
        return map;
    }

    @GetMapping("/cround/auth")
    public Map<String, String> auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> map = new HashMap<>();
        map.put("role", authentication.getAuthorities().toString());
        return map;
    }
}
