package croundteam.cround.member.service;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.DuplicateEmailException;
import croundteam.cround.member.exception.DuplicateNicknameException;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.exception.PasswordMisMatchException;
import croundteam.cround.member.repository.MemberRepository;
import croundteam.cround.member.service.dto.FollowRequest;
import croundteam.cround.member.service.dto.FollowResponse;
import croundteam.cround.member.service.dto.MemberSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;

    @Transactional
    public Long saveMember(final MemberSaveRequest memberSaveRequest) {
        validateDuplicateEmail(memberSaveRequest.getEmail());
        validateDuplicateNickname(memberSaveRequest.getNickname());
        validateIsSamePassword(memberSaveRequest);

        Member member = memberSaveRequest.toEntity();
        Member saveMember = memberRepository.save(member);

        return saveMember.getId();
    }

    @Transactional
    public FollowResponse followCreator(FollowRequest followRequest) {
        Member source = findMemberById(followRequest.getSourceId());
        Creator target = findCreatorById(followRequest.getTargetId());

        source.follow(target);

        return new FollowResponse(source.getId(), target.getMemberId());

    }

    @Transactional
    public FollowResponse unfollowCreator(FollowRequest followRequest) {
        Member source = findMemberById(followRequest.getSourceId());
        Creator target = findCreatorById(followRequest.getTargetId());

        source.unfollow(target);

        return new FollowResponse(source.getId(), target.getMemberId());
    }

    private Creator findCreatorById(Long targetId) {
        return creatorRepository.findById(targetId).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }

    private Member findMemberById(Long sourceId) {
        return memberRepository.findById(sourceId).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }

    public void validateDuplicateEmail(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    public void validateDuplicateNickname(String nickname) {
        if(memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

    private void validateIsSamePassword(MemberSaveRequest memberSaveRequest) {
        if(!memberSaveRequest.getPassword().equals(memberSaveRequest.getConfirmPassword())) {
            throw new PasswordMisMatchException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
