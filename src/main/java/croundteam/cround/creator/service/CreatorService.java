package croundteam.cround.creator.service;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.DuplicateCreatorPlatformActivityNameException;
import croundteam.cround.common.exception.member.DuplicateEmailException;
import croundteam.cround.common.exception.member.NotExistMemberException;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.dto.CreatorSaveRequest;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CreatorService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;

    public String createCreator(Long memberId, CreatorSaveRequest creatorSaveRequest) {
        validateCreator(creatorSaveRequest);

        Member member = findMemberById(memberId);
        Creator creator = creatorSaveRequest.toEntity();
        creator.addMember(member);

        Creator saveCreator = creatorRepository.save(creator);

        return saveCreator.getActivityName();
    }

    private void validateCreator(CreatorSaveRequest creatorSaveRequest) {
        if(creatorRepository.existByPlatformActivityName(creatorSaveRequest.getPlatformActivityName())) {
            throw new DuplicateCreatorPlatformActivityNameException(ErrorCode.DUPLICATE_PLATFORM_ACTIVITY_NAME);
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
