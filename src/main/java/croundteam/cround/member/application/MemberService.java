package croundteam.cround.member.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.DuplicateEmailException;
import croundteam.cround.member.exception.DuplicateNicknameException;
import croundteam.cround.member.exception.PasswordMisMatchException;
import croundteam.cround.member.infrastructure.MemberRepository;
import croundteam.cround.member.application.dto.MemberSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long saveMember(final MemberSaveRequest memberSaveRequest) {
        validateDuplicateEmail(memberSaveRequest.getEmail());
        validateDuplicateNickname(memberSaveRequest.getNickname());
        validateIsSamePassword(memberSaveRequest);

        Member member = memberSaveRequest.toEntity();
        Member saveMember = memberRepository.save(member);

        return saveMember.getId();
    }

    public void validateDuplicateEmail(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    public void validateDuplicateNickname(String nickname) {
        if(memberRepository.existsByNicknameName(nickname)) {
            throw new DuplicateNicknameException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

    private void validateIsSamePassword(MemberSaveRequest memberSaveRequest) {
        if(!memberSaveRequest.getPassword().equals(memberSaveRequest.getConfirmPassword())) {
            throw new PasswordMisMatchException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
