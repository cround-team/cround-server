package croundteam.cround.creator.domain;

import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.domain.Member;
import croundteam.cround.tag.domain.Tag;
import croundteam.cround.tag.domain.Tags;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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

        Platform platform = Platform.of("cround.com", "instagram", "crounder");
        Tags tags = Tags.toTagsByNames("크라운드 대표", "크라운드 직원");

        // when
        Creator creator = new Creator(member, platform, tags);
        Creator saveCreator = creatorRepository.save(creator);

        // then
        Creator findCreator = creatorRepository.findById(saveCreator.getId()).get();
        assertThat(findCreator).isNotNull();
    }

}