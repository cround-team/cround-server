package croundteam.cround.security;

import croundteam.cround.member.domain.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptEncoder {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encrypt(final String origin) {
        return passwordEncoder.encode(origin);
    }

    public static boolean isSamePassword(Member saveMember, String password) {
        return passwordEncoder.matches(password, saveMember.getPassword());
    }
}
