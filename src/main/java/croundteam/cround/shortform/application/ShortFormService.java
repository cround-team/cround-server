package croundteam.cround.shortform.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.CreatorRepository;
import croundteam.cround.creator.exception.InvalidCreatorException;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.infra.S3Uploader;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.shortform.application.dto.*;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.domain.ShortFormQueryRepository;
import croundteam.cround.shortform.domain.ShortFormRepository;
import croundteam.cround.shortform.exception.NotExistShortFormException;
import croundteam.cround.support.search.SearchCondition;
import croundteam.cround.support.vo.AppUser;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static croundteam.cround.common.fixtures.ConstantFixtures.SHORT_FORM_IMAGE_PATH_PREFIX;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShortFormService {

    private final CreatorRepository creatorRepository;
    private final ShortFormRepository shortFormRepository;
    private final ShortFormQueryRepository shortFormQueryRepository;
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long saveShortForm(MultipartFile file, LoginMember loginMember, ShortFormSaveRequest shortFormSaveRequest) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Creator creator = findCreatorByMember(member);

        String thumbnailImage = s3Uploader.uploadImage(file, SHORT_FORM_IMAGE_PATH_PREFIX);

        ShortForm shortForm = shortFormSaveRequest.toEntity();
        shortForm.addCreator(creator);
        shortForm.addThumbnailImage(thumbnailImage);

        ShortForm saveShortForm = shortFormRepository.save(shortForm);

        return saveShortForm.getId();
    }

    public SearchShortFormResponses searchShortForm(SearchCondition searchCondition, Pageable pageable, AppUser appUser) {
        Member member = getLoginMember(appUser);
        Slice<ShortForm> shortForms = shortFormQueryRepository.searchByCondition(searchCondition, pageable);

        return new SearchShortFormResponses(shortForms, member);
    }

    @Transactional
    public FindShortFormResponse findOne(Long shortsId, AppUser appUser) {
        ShortForm shortForm = findShortFormById(shortsId);
        Member member = getLoginMember(appUser);
        Creator creator = findCreatorIfExists(member);

        shortForm.increaseVisit();

        return FindShortFormResponse.from(shortForm, member, creator);
    }

    private ShortForm findShortFormById(Long shortsId) {
        return shortFormRepository.findShortFormById(shortsId).orElseThrow(() -> {
            throw new NotExistShortFormException(ErrorCode.NOT_EXIST_SHORT_FORM);
        });
    }

    public FindPopularShortForms findPopularShortForms(int size, AppUser appUser) {
        Member member = getLoginMember(appUser);

        List<ShortForm> popularVisitShortForms = shortFormQueryRepository.findPopularVisitShortForm(size);
        List<ShortForm> popularLikeShortForms = shortFormQueryRepository.findPopularLikeShortForm(size);
        List<ShortForm> popularBookmarkShortForms = shortFormQueryRepository.findPopularBookmarkShortForms(size);

        return new FindPopularShortForms(popularVisitShortForms, popularLikeShortForms, popularBookmarkShortForms, member);
    }

    @Transactional
    public void deleteShortForm(Long shortsId, LoginMember loginMember) {
        ShortForm shortForm = findShortFormById(shortsId);
        Member member = findMemberByEmail(loginMember.getEmail());
        Creator creator = findCreatorByMember(member);

        validateSameCreator(creator, shortForm);

        shortFormRepository.deleteById(shortsId);
    }

    @Transactional
    public void updateShortFrom(
            final Long shortsId, final ShortFormUpdateRequest shortFormUpdateRequest,
            final LoginMember loginMember, final MultipartFile file
    ) {
        ShortForm shortForm = findShortFormById(shortsId);
        Member member = findMemberByEmail(loginMember.getEmail());
        Creator creator = findCreatorByMember(member);

        validateSameCreator(creator, shortForm);

        String original = s3Uploader.uploadImageIfEquals(shortForm.getThumbnailImage(), file, SHORT_FORM_IMAGE_PATH_PREFIX);
        shortForm.update(shortFormUpdateRequest, original);
    }

    private Member getLoginMember(AppUser appUser) {
        if (appUser.isGuest()) {
            return null;
        }
        return findMemberByEmail(appUser.getEmail());
    }

    private static void validateSameCreator(Creator creator, ShortForm shortForm) {
        if(!shortForm.isAuthoredBy(creator)) {
            throw new InvalidCreatorException(ErrorCode.INVALID_AUTHORIZATION);
        }
    }

    private Creator findCreatorByMember(Member member) {
        return creatorRepository.findCreatorByMember(member).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }

    private Creator findCreatorIfExists(Member member) {
        return creatorRepository.findCreatorByMember(member).orElse(null);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
