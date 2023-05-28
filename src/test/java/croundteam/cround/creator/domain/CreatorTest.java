package croundteam.cround.creator.domain;

import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.creator.domain.tag.Tags;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static croundteam.cround.common.fixtures.ConstantFixtures.CREATOR_PLATFORM_URI;
import static croundteam.cround.common.fixtures.ConstantFixtures.DEFAULT_PROFILE_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CreatorTest {

    @Autowired
    CreatorRepository creatorRepository;

    @Test
    @DisplayName("Creator를 생성한다.")
    @Commit
    void create() {
        // given
        Member member = Member.builder()
                .username("cround")
                .password("password")
                .email("cround@cround.com")
                .build();

        Platform platform = Platform.of(CREATOR_PLATFORM_URI, "instagram", "crounder", "youtube");
        Tags tags = Tags.create("크라운드 대표", "크라운드 직원");

        // when
        Creator creator = Creator.of(ProfileImage.create(DEFAULT_PROFILE_IMAGE), member, platform, tags);
        Creator saveCreator = creatorRepository.save(creator);

        // then
        Creator findCreator = creatorRepository.findById(saveCreator.getId()).get();
        assertThat(findCreator).isNotNull();
    }

}