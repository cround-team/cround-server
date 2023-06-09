package croundteam.cround.support.vo;

import lombok.Getter;
import lombok.ToString;

import static org.springframework.util.StringUtils.hasText;

@Getter
public class AppUser {

    private String email;
    private boolean isGuest;

    public AppUser(String email) {
        this(email, hasText(email) ? false : true);
    }

    public AppUser(String email, boolean isGuest) {
        this.email = email;
        this.isGuest = isGuest;
    }
}
