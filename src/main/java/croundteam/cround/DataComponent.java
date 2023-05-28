package croundteam.cround;

import croundteam.cround.creator.domain.Creator;
import croundteam.cround.creator.domain.Description;
import croundteam.cround.creator.domain.ProfileImage;
import croundteam.cround.creator.domain.platform.Platform;
import croundteam.cround.creator.domain.tag.Tags;
import croundteam.cround.creator.repository.CreatorRepository;
import croundteam.cround.member.domain.AuthProvider;
import croundteam.cround.member.domain.Member;
import croundteam.cround.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Arrays;

import static croundteam.cround.common.fixtures.ConstantFixtures.DEFAULT_PLATFORM_URI;
import static croundteam.cround.common.fixtures.ConstantFixtures.DEFAULT_PROFILE_IMAGE;

@Component
@RequiredArgsConstructor
public class DataComponent {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;

    @PostConstruct
    public void init() {
        Member 감자 = createAndSaveMember("감자");
        Platform platform = createPlatform("캔다", "Youtube", "감자농장");
        createAndSaveCreator(감자, platform, Tags.castToTags(Arrays.asList("감자", "농장", "심는다")));

        Member 니나노 = createAndSaveMember("니나노");
        platform = createPlatform("릴리리", "TIKTOK", "닐리리");
        createAndSaveCreator(니나노, platform, Tags.castToTags(Arrays.asList("나는", "신나게", "부른다")));

        Member 한번에 = createAndSaveMember("한번에");
        platform = createPlatform("저스트원", "Youtube", "온니원");
        createAndSaveCreator(한번에, platform, Tags.castToTags(Arrays.asList("젭알", "한번에", "끝내자")));

        Member 농부 = createAndSaveMember("으쌰");
        platform = createPlatform("농부", "PODCAST", "노옹부");
        createAndSaveCreator(농부, platform, Tags.castToTags(Arrays.asList("나는", "농부여")));

        Member 치킨 = createAndSaveMember("치킨");
        platform = createPlatform(치킨.getNickname(), "instagram", "치이킨");
        createAndSaveCreator(치킨, platform, Tags.castToTags(Arrays.asList("꼬끼오", "꼬꼬")));

        Member 육육 = createAndSaveMember("육육");
        platform = createPlatform(육육.getNickname(), "instagram", "유욱");
        createAndSaveCreator(육육, platform, Tags.castToTags(Arrays.asList("육육", "유욱")));

        Member 뇽뇽 = createAndSaveMember("뇽뇽");
        platform = createPlatform(뇽뇽.getNickname(), "podcast", "니요옹");
        createAndSaveCreator(뇽뇽, platform, Tags.castToTags(Arrays.asList("뇨옹", "뇽뇽", "뇨오오오옹")));
    }

    private Platform createPlatform(String platformHeadTheme, String platformHeadType, String platformActivityName) {
        return Platform.of(platformHeadTheme, platformHeadType, DEFAULT_PLATFORM_URI, platformActivityName);
    }

    private Creator createAndSaveCreator(Member member, Platform platform, Tags tags) {
        Creator creator = Creator.builder()
                .profileImage(ProfileImage.create(DEFAULT_PROFILE_IMAGE))
                .description(Description.create(member.getNickname()))
                .member(member)
                .platform(platform)
                .tags(tags)
                .build();

        return creatorRepository.save(creator);
    }

    public Member createAndSaveMember(String value) {
        Member member = Member.builder()
                .nickname(value)
                .username(value)
                .email(value + "@cround.co.kr")
                .authProvider(AuthProvider.LOCAL)
                .build();
        return memberRepository.save(member);
    }
}
