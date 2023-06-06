package croundteam.cround.creator.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.tag.domain.CreatorTag;
import croundteam.cround.creator.exception.IncorrectSourceException;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.creator.infrastructure.CreatorQueryRepository;
import croundteam.cround.creator.infrastructure.CreatorRepository;
import croundteam.cround.creator.infrastructure.CreatorTagRepository;
import croundteam.cround.creator.application.dto.CreatorSaveRequest;
import croundteam.cround.creator.application.dto.FindCreatorResponse;
import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.creator.application.dto.SearchCreatorResponses;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.DuplicateNicknameException;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.infrastructure.MemberRepository;
import croundteam.cround.security.support.LoginMember;
import croundteam.cround.security.support.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatorService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final CreatorQueryRepository creatorQueryRepository;
    private final CreatorTagRepository creatorTagRepository;

    @Transactional
    public Long createCreator(LoginMember loginMember, CreatorSaveRequest creatorSaveRequest) {
        validateDuplicateNickname(creatorSaveRequest.getNickname());

        Member member = findMemberByEmail(loginMember.getEmail());
        validateSameSource(loginMember, member);

        Creator creator = creatorSaveRequest.toEntity();
        creator.addMember(member);

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
