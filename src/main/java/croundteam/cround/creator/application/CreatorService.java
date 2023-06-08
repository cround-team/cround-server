package croundteam.cround.creator.application;

import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.application.dto.CreatorSaveRequest;
import croundteam.cround.creator.application.dto.FindCreatorResponse;
import croundteam.cround.creator.application.dto.SearchCreatorResponses;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.tag.CreatorTag;
import croundteam.cround.creator.exception.IncorrectSourceException;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.creator.domain.CreatorQueryRepository;
import croundteam.cround.creator.domain.CreatorRepository;
import croundteam.cround.creator.domain.CreatorTagRepository;
import croundteam.cround.infra.S3Uploader;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.DuplicateNicknameException;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.domain.MemberRepository;
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

import static croundteam.cround.common.fixtures.ConstantFixtures.CREATOR_IMAGE_PATH_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatorService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final CreatorQueryRepository creatorQueryRepository;
    private final CreatorTagRepository creatorTagRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long createCreator(MultipartFile file, LoginMember loginMember, CreatorSaveRequest creatorSaveRequest) {
        validateDuplicateNickname(creatorSaveRequest.getNickname());

        Member member = findMemberByEmail(loginMember.getEmail());
        validateSameSource(loginMember, member);

        String profileImage = s3Uploader.uploadImage(file, CREATOR_IMAGE_PATH_PREFIX);

        Creator creator = creatorSaveRequest.toEntity();
        creator.addMember(member);
        creator.addProfileImage(profileImage);

        Creator saveCreator = creatorRepository.save(creator);

        return saveCreator.getId();
    }

    public SearchCreatorResponses searchCreatorsByCondition(SearchCondition searchCondition, Pageable pageable) {
        Slice<Creator> creators = creatorQueryRepository.searchByCondition(searchCondition, pageable);
        return new SearchCreatorResponses(creators);
    }

    public FindCreatorResponse findOne(AppUser appUser, Long creatorId) {
        Member member = getLoginMember(appUser);
        Creator creator = findCreatorWithJoinById(creatorId);

        List<CreatorTag> creatorTags = creatorTagRepository.findCreatorTagById(creatorId);
        creator.addTags(creatorTags);

        return new FindCreatorResponse(creator, member);
    }

    public void validateDuplicateNickname(String nickname) {
        if(creatorRepository.existsByNicknameName(nickname)) {
            throw new DuplicateNicknameException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private Member getLoginMember(AppUser appUser) {
        if (appUser.isGuest()) {
            return null;
        }
        return findMemberByEmail(appUser.getEmail());
    }

    /**
     * 페치 조인 쿼리 (1 + 1 + 1)
     * 1: Creator, Member, Creator_Platform
     * 2: Tag, CreatorTag
     * 3: Count(Follow)
     */
    private Creator findCreatorWithJoinById(Long creatorId) {
        return creatorRepository.findCreatorById(creatorId).orElseThrow(() -> {
            throw new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR);
        });
    }

    /**
     * 일반 쿼리 (1 + N)
     * 1: Creator
     * 2: Follow
     * 3: CreatorTag
     * 4: Tag
     * 5: Creator_Platform
     */
    private Creator findCreatorById(Long creatorId) {
        return creatorRepository.findById(creatorId).orElseThrow(() -> {
            throw new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR);
        });
    }

    private void validateSameSource(LoginMember loginMember, Member member) {
        if (!loginMember.getEmail().equals(member.getEmail())) {
            throw new IncorrectSourceException(ErrorCode.INCORRECT_SOURCE);
        }
    }
}
