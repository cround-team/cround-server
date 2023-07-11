package croundteam.cround.member.domain;

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
//        List<PlatformTypeTemp> platformTypes = Arrays.asList(PlatformTypeTemp.create("YOUTUBE"), PlatformTypeTemp.create("TIKTOK"));
//        InterestPlatforms interest = InterestPlatforms.create(platformTypes);
//        InterestPlatforms emptyInterest = InterestPlatforms.create(Collections.emptyList());
//
//        Member member = Member.builder()
//                .username("cround")
//                .interest(emptyInterest)
//                .email("cround@cround.com")
//                .build();
//
//        memberRepository.save(member);
    }

}
