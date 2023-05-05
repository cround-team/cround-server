package croundteam.cround.member.service;

import croundteam.cround.auth.support.BCryptEncoder;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.DuplicateEmailException;
import croundteam.cround.common.exception.member.PasswordMisMatchException;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.dto.MemberSaveRequest;
import croundteam.cround.member.repository.MemberRepository;
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
        validateDuplicateEmail(memberSaveRequest);
        validateIsSamePassword(memberSaveRequest);

        Member member = Member.builder()
                .userName(memberSaveRequest.getUsername())
                .email(memberSaveRequest.getEmail())
                .password(BCryptEncoder.encrypt(memberSaveRequest.getPassword()))
                .build();
        Member saveMember = memberRepository.save(member);

        return saveMember.getId();
    }

    private void validateIsSamePassword(MemberSaveRequest memberSaveRequest) {
        if(!memberSaveRequest.getPassword().equals(memberSaveRequest.getConfirmPassword())) {
            throw new PasswordMisMatchException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    private void validateDuplicateEmail(MemberSaveRequest memberSaveRequest) {
        if (memberRepository.existsByEmail(memberSaveRequest.getEmail())) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
    }
}
