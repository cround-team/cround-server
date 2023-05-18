package croundteam.cround.like;

import croundteam.cround.like.service.LikeService;
import croundteam.cround.member.dto.LoginMember;
import croundteam.cround.security.token.support.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/boards/{boardId}")
    public void likeBoard(@Login LoginMember loginMember, @PathVariable Long boardId) {


    }
}
