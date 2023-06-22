package croundteam.cround.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "비회원"),
    USER("ROLE_USER", "회원"),
    CREATOR("ROLE_CREATOR", "크리에이터"),
    ADMIN("ROLE_ADMIN", "관리자");

    private String code;
    private String name;
}
