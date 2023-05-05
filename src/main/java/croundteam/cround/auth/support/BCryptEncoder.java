package croundteam.cround.auth.support;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptEncoder {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encrypt(final String origin) {
        return passwordEncoder.encode(origin);
    }

    public static boolean isSamePassword(String password) {
        return passwordEncoder.matches("memberLoginRequest.getPassword()", password);
    }
}
