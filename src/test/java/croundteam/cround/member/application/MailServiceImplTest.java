package croundteam.cround.member.application;

import croundteam.cround.infra.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
class MailServiceImplTest {

    @Autowired
    protected MailService mailService;

    @Test
    void sendTest() {
        mailService.send("cround@cround.com", "password");

    }

}