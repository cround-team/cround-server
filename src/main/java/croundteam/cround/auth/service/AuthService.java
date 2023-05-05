package croundteam.cround.auth.service;

import croundteam.cround.common.dto.TokenResponse;
import croundteam.cround.member.dto.MemberLoginRequest;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public TokenResponse login(final MemberLoginRequest memberLoginRequest) {

    }
}
