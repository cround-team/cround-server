package croundteam.cround.creator.service;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.exception.DuplicateCreatorPlatformActivityNameException;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.creator.service.dto.CreatorSaveRequest;
import croundteam.cround.creator.service.dto.SearchCreatorResponse;
import croundteam.cround.creator.service.dto.SearchCondition;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.repository.MemberRepository;
import croundteam.cround.member.service.dto.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatorService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;

    @Transactional
    public String createCreator(LoginMember loginMember, CreatorSaveRequest creatorSaveRequest) {
        validateCreator(creatorSaveRequest);

        Member member = findMemberByEmail(loginMember.getEmail());
        Creator creator = creatorSaveRequest.toEntity();
        creator.addMember(member);

        Creator saveCreator = creatorRepository.save(creator);

        return saveCreator.getActivityName();
    }


    public Page<SearchCreatorResponse> searchCreatorsByCondition(SearchCondition searchCondition) {
        Pageable pageable = searchCondition.toPageRequest();
        Page<Creator> creators = creatorRepository.searchCreatorByKeywordAndPlatforms(searchCondition, pageable);

        return creators.map(SearchCreatorResponse::from);
    }

    private void validateCreator(CreatorSaveRequest creatorSaveRequest) {
        if(creatorRepository.existsByPlatformPlatformActivityNameName(creatorSaveRequest.getPlatformActivityName())) {
            throw new DuplicateCreatorPlatformActivityNameException(ErrorCode.DUPLICATE_PLATFORM_ACTIVITY_NAME);
        }
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
