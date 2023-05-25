package croundteam.cround.creator.service;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.exception.DuplicateCreatorPlatformActivityNameException;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.creator.service.dto.CreatorSaveRequest;
import croundteam.cround.creator.service.dto.CreatorSearchResponse;
import croundteam.cround.creator.service.dto.SearchCreatorCondition;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public String createCreator(Long memberId, CreatorSaveRequest creatorSaveRequest) {
        validateCreator(creatorSaveRequest);

        Member member = findMemberById(memberId);
        Creator creator = creatorSaveRequest.toEntity();
        creator.addMember(member);

        Creator saveCreator = creatorRepository.save(creator);

        return saveCreator.getActivityName();
    }


    public Page<CreatorSearchResponse> searchCreatorsByCondition(
            SearchCreatorCondition searchCreatorCondition,
            Pageable pageable
    ) {
        Page<Creator> creators = creatorRepository.searchCreatorByKeywordAndPlatforms(searchCreatorCondition, pageable);
        return creators.map(CreatorSearchResponse::from);
    }

    private void validateCreator(CreatorSaveRequest creatorSaveRequest) {
        if(creatorRepository.existsByPlatformPlatformActivityNameName(creatorSaveRequest.getPlatformActivityName())) {
            throw new DuplicateCreatorPlatformActivityNameException(ErrorCode.DUPLICATE_PLATFORM_ACTIVITY_NAME);
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
