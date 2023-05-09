package croundteam.cround.member.service;

import croundteam.cround.security.token.RefreshToken;
import croundteam.cround.security.token.RefreshTokenRepository;
import croundteam.cround.security.BCryptEncoder;
import croundteam.cround.security.token.TokenProvider;
import croundteam.cround.common.dto.TokenResponse;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.NotExistMemberException;
import croundteam.cround.common.exception.member.PasswordMisMatchException;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.dto.MemberLoginRequest;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenResponse login(final MemberLoginRequest memberLoginRequest) {
        Member member = findMemberByEmail(memberLoginRequest);

        validateIsSamePassword(member, memberLoginRequest);

        TokenResponse tokenResponse = issueToken(member);
        refreshTokenRepository.save(createRefreshToken(tokenResponse, member.getId()));

        return tokenResponse;
    }

    private RefreshToken createRefreshToken(TokenResponse tokenResponse, Long memberId) {
        return new RefreshToken(tokenResponse.excludeBearerInRefreshToken(), memberId);
    }

    private TokenResponse issueToken(Member member) {
        return tokenProvider.generateToken(member.getEmail());

    }

    private void validateIsSamePassword(Member member, MemberLoginRequest memberLoginRequest) {
        if(!BCryptEncoder.isSamePassword(member, memberLoginRequest.getPassword())) {
            throw new PasswordMisMatchException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    private Member findMemberByEmail(MemberLoginRequest memberLoginRequest) {
        return memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
