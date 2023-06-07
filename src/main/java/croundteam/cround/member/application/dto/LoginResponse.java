package croundteam.cround.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;

    public static LoginResponse create(String token) {
        return new LoginResponse(token);
    }
}
