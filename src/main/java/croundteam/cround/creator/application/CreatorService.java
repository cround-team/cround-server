package croundteam.cround.creator.application;

import croundteam.cround.board.application.dto.SearchBoardsResponses;
import croundteam.cround.board.domain.Board;
import croundteam.cround.board.domain.BoardQueryRepository;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.application.dto.*;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.CreatorQueryRepository;
import croundteam.cround.creator.domain.CreatorRepository;
import croundteam.cround.creator.domain.CreatorTagRepository;
import croundteam.cround.creator.domain.tag.CreatorTag;
import croundteam.cround.creator.exception.IncorrectSourceException;
import croundteam.cround.creator.exception.InvalidCreatorException;
import croundteam.cround.creator.exception.NotExistCreatorException;
import croundteam.cround.infra.S3Uploader;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.DuplicateNicknameException;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.shortform.application.dto.SearchShortFormResponses;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.domain.ShortFormQueryRepository;
import croundteam.cround.support.search.BaseSearchCondition;
import croundteam.cround.support.search.SearchCondition;
import croundteam.cround.support.vo.AppUser;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static croundteam.cround.common.fixtures.ConstantFixtures.CREATOR_IMAGE_PATH_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatorService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final CreatorTagRepository creatorTagRepository;
    private final CreatorQueryRepository creatorQueryRepository;
    private final ShortFormQueryRepository shortFormQueryRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public CreatorSaveResponse createCreator(MultipartFile file, LoginMember loginMember, CreatorSaveRequest creatorSaveRequest) {
        validateDuplicateNickname(creatorSaveRequest.getNickname());

        Member member = findMemberByEmail(loginMember.getEmail());
        validateSameSource(loginMember, member);

        String profileImage = s3Uploader.uploadImage(file, CREATOR_IMAGE_PATH_PREFIX);

        Creator creator = creatorSaveRequest.toEntity();
        creator.addMember(member);
        creator.addProfileImage(profileImage);

        Creator saveCreator = creatorRepository.save(creator);

        return CreatorSaveResponse.create(saveCreator);
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

    public SearchShortFormResponses findShortsByCreator(Long creatorId, AppUser appUser, BaseSearchCondition searchCondition) {
        Creator creator = findCreatorById(creatorId);
        Member member = getLoginMember(appUser);

        Slice<ShortForm> shortForms = shortFormQueryRepository.findShortsByCreatorAndCondition(creator.getId(), searchCondition);

        return new SearchShortFormResponses(shortForms, member);
    }

    public SearchBoardsResponses findBoardsByCreator(Long creatorId, AppUser appUser, BaseSearchCondition searchCondition) {
        Creator creator = findCreatorById(creatorId);
        Member member = getLoginMember(appUser);

        Slice<Board> boards = boardQueryRepository.findBoardsByCreatorAndCondition(creator.getId(), searchCondition);
        return new SearchBoardsResponses(boards, member);
    }

    public FindHomeCreators findHomeCreators(int size, AppUser appUser) {
        Member member = getLoginMember(appUser);

        List<Creator> latestCreators = creatorRepository.findCreatorSortByLatest(PageRequest.ofSize(size));
        List<Creator> interestCreators = creatorQueryRepository.findCreatorByInterestPlatform(size, getInterestPlatformBy(member));

        return new FindHomeCreators(latestCreators, interestCreators);
    }

    @Transactional
    public CreatorUpdateResponse updateCreator(MultipartFile file, CreatorUpdateRequest creatorUpdateRequest, LoginMember loginMember) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Creator creator = findCreatorByMember(member);

        validateSameSource(loginMember, member);

        creatorTagRepository.deleteByCreator(creator);
        String profileImage = s3Uploader.uploadImageIfEquals(creator.getProfileImage(), file, CREATOR_IMAGE_PATH_PREFIX);
        creator.update(creatorUpdateRequest, member, profileImage);

        return CreatorUpdateResponse.create(creator);
    }

    private List<String> getInterestPlatformBy(Member member) {
        if(Objects.isNull(member)) {
            return Collections.emptyList();
        }
        return member.getInterestPlatforms();
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

    private Creator findCreatorWithJoinById(Long creatorId) {
        return creatorRepository.findCreatorById(creatorId).orElseThrow(() -> {
            throw new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR);
        });
    }

    private Creator findCreatorById(Long creatorId) {
        return creatorRepository.findById(creatorId).orElseThrow(() -> {
            throw new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR);
        });
    }

    private Creator findCreatorByMember(Member member) {
        return creatorRepository.findCreatorByMember(member).orElseThrow(() -> {
            throw new InvalidCreatorException(ErrorCode.INVALID_AUTHORIZATION);
        });
    }

    private void validateSameSource(LoginMember loginMember, Member member) {
        if (!loginMember.getEmail().equals(member.getEmail())) {
            throw new IncorrectSourceException(ErrorCode.INCORRECT_SOURCE);
        }
    }
}
