package croundteam.cround.member.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.DuplicateEmailException;
import croundteam.cround.member.exception.DuplicateNicknameException;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.exception.PasswordMisMatchException;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.application.dto.MemberSaveRequest;
import croundteam.cround.shortform.application.dto.SearchShortFormResponses;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.domain.ShortFormQueryRepository;
import croundteam.cround.support.search.SimpleSearchCondition;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final ShortFormQueryRepository shortFormQueryRepository;

    @Transactional
    public Long saveMember(final MemberSaveRequest memberSaveRequest) {
        validateDuplicateEmail(memberSaveRequest.getEmail());
        validateDuplicateNickname(memberSaveRequest.getNickname());
        validateIsSamePassword(memberSaveRequest);

        Member member = memberSaveRequest.toEntity();
        Member saveMember = memberRepository.save(member);

        return saveMember.getId();
    }

    public SearchShortFormResponses findShortFormOwnBookmarks(
            LoginMember loginMember,
            SimpleSearchCondition searchCondition
    ) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Slice<ShortForm> shortForms = shortFormQueryRepository.findOwnBookmarkBy(member.getId(), searchCondition);

        return new SearchShortFormResponses(shortForms, member);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER);
        });
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
