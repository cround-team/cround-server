package croundteam.cround.shorts.service;

import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.common.exception.member.NotExistCreatorException;
import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.dto.LoginMember;
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

    @Transactional
    public Long shortsSaveRequest(LoginMember member, ShortsSaveRequest shortsSaveRequest) {
        Creator creator = findCreatorByEmail(member.getEmail());
        Shorts shorts = Shorts.of(creator, shortsSaveRequest);
        creator.addShorts(shorts);

        Shorts saveShorts = shortsRepository.save(shorts);

        return saveShorts.getId();
    }

    private Creator findCreatorByEmail(String email) {
        return creatorRepository.findCreatorByEmail(email).orElseThrow(
                () -> new NotExistCreatorException(ErrorCode.NOT_EXIST_CREATOR));
    }
}
