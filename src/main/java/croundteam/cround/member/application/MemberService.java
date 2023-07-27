package croundteam.cround.member.application;

import croundteam.cround.board.application.dto.SearchBoardsResponses;
import croundteam.cround.board.domain.Board;
import croundteam.cround.board.domain.BoardQueryRepository;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.application.dto.SearchCreatorResponses;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.CreatorQueryRepository;
import croundteam.cround.member.application.dto.FindMemberResponse;
import croundteam.cround.member.application.dto.MemberSaveRequest;
import croundteam.cround.member.application.dto.MemberUpdateRequest;
import croundteam.cround.member.application.dto.PasswordChangeRequest;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.domain.MemberRepository;
import croundteam.cround.member.exception.DuplicateEmailException;
import croundteam.cround.member.exception.DuplicateNicknameException;
import croundteam.cround.member.exception.NotExistMemberException;
import croundteam.cround.member.exception.PasswordMisMatchException;
import croundteam.cround.shortform.application.dto.SearchShortFormResponses;
import croundteam.cround.shortform.domain.ShortForm;
import croundteam.cround.shortform.domain.ShortFormQueryRepository;
import croundteam.cround.support.search.BaseSearchCondition;
import croundteam.cround.support.vo.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static croundteam.cround.support.BCryptEncoder.encrypt;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final CreatorQueryRepository creatorQueryRepository;
    private final ShortFormQueryRepository shortFormQueryRepository;
    private final BoardQueryRepository boardQueryRepository;

    @Transactional
    public Long saveMember(final MemberSaveRequest memberSaveRequest) {
        validateDuplicateEmail(memberSaveRequest.getEmail());
        validateDuplicateNickname(memberSaveRequest.getNickname());
        validateSamePassword(memberSaveRequest.getPassword(), memberSaveRequest.getConfirmPassword());

        Member member = memberSaveRequest.toEntity();
        Member saveMember = memberRepository.save(member);

        return saveMember.getId();
    }

    public SearchShortFormResponses findShortFormOwnBookmarks(
            LoginMember loginMember,
            BaseSearchCondition searchCondition
    ) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Slice<ShortForm> shortForms = shortFormQueryRepository.findOwnBookmarkBy(member.getId(), searchCondition);

        return new SearchShortFormResponses(shortForms, member);
    }

    public SearchBoardsResponses findBoardsOwnBookmarks(
            LoginMember loginMember,
            BaseSearchCondition searchCondition
    ) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Slice<Board> boards = boardQueryRepository.findOwnBookmarkBy(member.getId(), searchCondition);

        return new SearchBoardsResponses(boards, member);
    }

    public SearchCreatorResponses findCreatorOwnFollowings(
            LoginMember loginMember,
            BaseSearchCondition searchCondition
    ) {
        Member member = findMemberByEmail(loginMember.getEmail());
        Slice<Creator> creators = creatorQueryRepository.findOwnFollowingBy(member.getId(), searchCondition);

        return new SearchCreatorResponses(creators);
    }

    @Transactional
    public void updateMember(MemberUpdateRequest memberUpdateRequest, LoginMember loginMember) {
        Member member = findMemberByEmail(loginMember.getEmail());
        member.updateMember(memberUpdateRequest);
    }

    @Transactional
    public void changePasswordByMailCertification(PasswordChangeRequest passwordChangeRequest) {
        validateSamePassword(passwordChangeRequest.getPassword(), passwordChangeRequest.getConfirmPassword());

        Member member = findMemberById(passwordChangeRequest);
        member.validateAuthorizationCode(passwordChangeRequest.getCode());
        member.changePassword(encrypt(passwordChangeRequest.getPassword()));
    }

    public FindMemberResponse findMember(LoginMember loginMember) {
        Member member = findMemberByEmail(loginMember.getEmail());
        return new FindMemberResponse(member);
    }

    public void validateDuplicateEmail(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    public void validateDuplicateNickname(String nickname) {
        if(memberRepository.existsByNicknameName(nickname)) {
            throw new DuplicateNicknameException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER);
        });
    }

    private Member findMemberById(PasswordChangeRequest passwordChangeRequest) {
        return memberRepository.findById(passwordChangeRequest.getId()).orElseThrow(() -> {
            throw new NotExistMemberException(ErrorCode.NOT_EXIST_MEMBER);
        });
    }

    private void validateSamePassword(String password, String confirmPassword) {
        if(!password.equals(confirmPassword)) {
            throw new PasswordMisMatchException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
