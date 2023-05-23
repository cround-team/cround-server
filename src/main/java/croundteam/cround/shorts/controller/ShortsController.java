package croundteam.cround.shorts.controller;

import croundteam.cround.member.dto.LoginMember;
import croundteam.cround.security.token.support.Login;
import croundteam.cround.shorts.dto.ShortsSaveRequest;
import croundteam.cround.shorts.service.ShortsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shorts")
public class ShortsController {

    private final ShortsService shortsService;

    @PostMapping
    public ResponseEntity<Void> saveShorts(
            @Login LoginMember member,
            @RequestBody @Valid ShortsSaveRequest shortsSaveRequest) {
        Long shortsId = shortsService.shortsSaveRequest(member, shortsSaveRequest);

        return ResponseEntity.created(URI.create("/api/shorts/" + shortsId)).build();
    }

}
