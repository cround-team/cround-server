package croundteam.cround.shorts.controller;

import croundteam.cround.member.dto.LoginMember;
import croundteam.cround.security.token.support.Login;
import croundteam.cround.shorts.dto.ShortsSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shorts")
public class ShortsController {

    @PostMapping
    public void saveShorts(
            @Login LoginMember member,
            @RequestBody @Valid ShortsSaveRequest shortsSaveRequest) {

    }

}
