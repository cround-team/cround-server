package croundteam.cround.shorts.service;

import croundteam.cround.board.service.dto.BookmarkResponse;
import croundteam.cround.board.service.dto.LikeResponse;
import croundteam.cround.common.dto.SearchCondition;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.repository.MemberRepository;
import croundteam.cround.member.service.dto.LoginMember;
import croundteam.cround.security.support.AppUser;
import croundteam.cround.shorts.domain.Shorts;
import croundteam.cround.shorts.exception.NotExistShortsException;
import croundteam.cround.shorts.repository.ShortsQueryRepository;
import croundteam.cround.shorts.repository.ShortsRepository;
import croundteam.cround.shorts.service.dto.FindShortsResponse;
import croundteam.cround.shorts.service.dto.SearchShortsResponses;
import croundteam.cround.shorts.service.dto.ShortsSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShortsService {

    private final CreatorRepository creatorRepository;
    private final ShortsRepository shortsRepository;
    private final ShortsQueryRepository shortsQueryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long shortsSaveRequest(LoginMember member, ShortsSaveRequest shortsSaveRequest) {
        Creator creator = findCreatorByEmail(member.getEmail());
        Shorts shorts = Shorts.of(creator, shortsSaveRequest);
        creator.addShorts(shorts);

        Shorts saveShorts = shortsRepository.save(shorts);

        return saveShorts.getId();
    }

    public SearchShortsResponses searchShorts(SearchCondition searchCondition, Pageable pageable, AppUser appUser) {
        Member member = getLoginMember(appUser);

        Slice<Shorts> shorts = shortsQueryRepository.searchByCondition(searchCondition, pageable);
        System.out.println("=====================");
        return new SearchShortsResponses(shorts, member);
    }

    public FindShortsResponse findOne(Long shortsId, AppUser appUser) {
        Member member = getLoginMember(appUser);
        Shorts shorts = shortsRepository.findShortsWithJoinById(shortsId);

        return FindShortsResponse.from(shorts, member);
    }

    @Transactional
    public BookmarkResponse bookmarkShorts(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Shorts shorts = findShortsById(shortsId);

        shorts.bookmark(member);

        return new BookmarkResponse(shorts.getShortsBookmarks());
    }

    @Transactional
    public BookmarkResponse unbookmarkShorts(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Shorts shorts = findShortsById(shortsId);

        shorts.unbookmark(member);

        return new BookmarkResponse(shorts.getShortsBookmarks());
    }

    @Transactional
    public LikeResponse likeShorts(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Shorts shorts = findShortsById(shortsId);

        shorts.like(member);

        return new LikeResponse(shorts.getShortsLikes());
    }

    @Transactional
    public LikeResponse unlikeShorts(LoginMember loginMember, Long shortsId) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Shorts shorts = findShortsById(shortsId);

        shorts.unlike(member);

        return new LikeResponse(shorts.getShortsLikes());
    }

    private Member getLoginMember(AppUser appUser) {
        if (appUser.isGuest()) {
            return null;
        }
        return findMemberByEmail(appUser.getEmail());
    }

    private Creator findCreatorByEmail(String email) {
        return creatorRepository.findCreatorByEmail(email).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER));
    }

    private Shorts findShortsById(Long shortsId) {
        return shortsRepository.findById(shortsId).orElseThrow(
                () -> new NotExistShortsException(ErrorCode.NOT_EXIST_SHORTS));
    }
}
