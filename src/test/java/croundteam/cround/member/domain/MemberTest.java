package croundteam.cround.member.domain;

import croundteam.cround.creator.domain.platform.PlatformType;
import croundteam.cround.member.domain.interest.Interest;
import croundteam.cround.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void member_save() {
        List<PlatformType> platformTypes = Arrays.asList(PlatformType.from("YOUTUBE"), PlatformType.from("TIKTOK"));
        Interest interest = Interest.from(platformTypes);
        Interest emptyInterest = Interest.from(Collections.emptyList());

        Member member = Member.builder()
                .username("cround")
                .interest(emptyInterest)
                .email("cround@cround.com")
                .build();

        memberRepository.save(member);
    }

}
