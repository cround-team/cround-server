package croundteam.cround.shorts.service;

import croundteam.cround.board.domain.Board;
import croundteam.cround.board.dto.BookmarkResponse;
import croundteam.cround.board.dto.LikeResponse;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.like.NotExistBoardException;
import croundteam.cround.common.exception.like.NotExistShortsException;
import croundteam.cround.common.exception.member.NotExistCreatorException;
import croundteam.cround.common.exception.member.NotExistMemberException;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.dto.LoginMember;
import croundteam.cround.member.repository.MemberRepository;
import croundteam.cround.shorts.domain.Shorts;
import croundteam.cround.shorts.dto.ShortsSaveRequest;
import croundteam.cround.shorts.repository.ShortsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShortsService {

    private final CreatorRepository creatorRepository;
    private final ShortsRepository shortsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long shortsSaveRequest(LoginMember member, ShortsSaveRequest shortsSaveRequest) {
        Creator creator = findCreatorByEmail(member.getEmail());
        Shorts shorts = Shorts.of(creator, shortsSaveRequest);
        creator.addShorts(shorts);

        Shorts saveShorts = shortsRepository.save(shorts);

        return saveShorts.getId();
    }

    @Transactional
    public BookmarkResponse bookmarkShorts(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Shorts shorts = findBoardById(shortsId);

        shorts.bookmark(member);

        return new BookmarkResponse(shorts.getShortsBookmarks());
    }

//    @Transactional
//    public BookmarkResponse unbookmarkShorts(LoginMember loginMember, Long shortsId) {
//    }
//
//    @Transactional
//    public LikeResponse likeShorts(LoginMember loginMember, Long shortsId) {
//    }
//
//    @Transactional
//    public LikeResponse unlikeShorts(LoginMember loginMember, Long shortsId) {
//    }

    private Creator findCreatorByEmail(String email) {
        return creatorRepository.findCreatorByEmail(email).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private Shorts findBoardById(Long shortsId) {
        return shortsRepository.findById(shortsId).orElseThrow(
                () -> new NotExistShortsException(ErrorCode.NOT_EXIST_SHORTS));
    }

}
