package croundteam.cround.shortform.application;

import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.creator.infrastructure.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.infrastructure.MemberRepository;
import croundteam.cround.security.support.AppUser;
import croundteam.cround.security.support.LoginMember;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.infrastructure.ShortFormQueryRepository;
import croundteam.cround.shortform.infrastructure.ShortFormRepository;
import croundteam.cround.shortform.application.dto.FindShortFormResponse;
import croundteam.cround.shortform.application.dto.SearchShortFormResponses;
import croundteam.cround.shortform.application.dto.ShortFormSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShortFormService {

    private final CreatorRepository creatorRepository;
    private final ShortFormRepository shortFormRepository;
    private final ShortFormQueryRepository shortFormQueryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveShortForm(LoginMember member, ShortFormSaveRequest shortFormSaveRequest) {
        Creator creator = findCreatorByEmail(member.getEmail());
        ShortForm shortForm = ShortForm.of(creator, shortFormSaveRequest);
        creator.addShorts(shortForm);

        ShortForm saveShortForm = shortFormRepository.save(shortForm);

        return saveShortForm.getId();
    }

    public SearchShortFormResponses searchShortForm(SearchCondition searchCondition, Pageable pageable, AppUser appUser) {
        Member member = getLoginMember(appUser);

        Slice<ShortForm> shortForms = shortFormQueryRepository.searchByCondition(searchCondition, pageable);
        return new SearchShortFormResponses(shortForms, member);
    }

    public FindShortFormResponse findOne(Long shortsId, AppUser appUser) {
        Member member = getLoginMember(appUser);
        ShortForm shortForm = shortFormRepository.findShortFormWithJoinById(shortsId);

        return FindShortFormResponse.from(shortForm, member);
    }

    private Member getLoginMember(AppUser appUser) {
        if (appUser.isGuest()) {
            return null;
        }
        return findMemberByEmail(appUser.getEmail());
    }

    private Creator findCreatorByEmail(String email) {
        return creatorRepository.findCreatorByEmail(email).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
