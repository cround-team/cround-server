package croundteam.cround.follow.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.creator.infrastructure.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.infrastructure.MemberRepository;
import croundteam.cround.follow.application.dto.FollowRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FollowService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;

    @Transactional
    public void followCreator(FollowRequest followRequest) {
        Member source = findMemberById(followRequest.getSourceId());
        Creator target = findCreatorById(followRequest.getTargetId());

        source.follow(target);
    }

    @Transactional
    public void unfollowCreator(FollowRequest followRequest) {
        Member source = findMemberById(followRequest.getSourceId());
        Creator target = findCreatorById(followRequest.getTargetId());

        source.unfollow(target);
    }

    private Creator findCreatorById(Long targetId) {
        return creatorRepository.findById(targetId).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }

    private Member findMemberById(Long sourceId) {
        return memberRepository.findById(sourceId).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
