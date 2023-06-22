package croundteam.cround.follow.application;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.creator.domain.CreatorRepository;
import croundteam.cround.follow.application.dto.FollowResponse;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.support.vo.LoginMember;
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
    public FollowResponse followCreator(Long creatorId, LoginMember loginMember) {
        Member source = findMemberByEmail(loginMember.getEmail());
        Creator target = findCreatorById(creatorId);

        source.follow(target);

        return new FollowResponse(source, target);
    }

    @Transactional
    public FollowResponse unfollowCreator(Long creatorId, LoginMember loginMember) {
        Member source = findMemberByEmail(loginMember.getEmail());
        Creator target = findCreatorById(creatorId);

        source.unfollow(target);

        return new FollowResponse(source, target);
    }

    private Creator findCreatorById(Long targetId) {
        return creatorRepository.findById(targetId).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
