package croundteam.cround.creator.controller;

import croundteam.cround.creator.service.dto.SearchCreatorResponse;
import croundteam.cround.creator.service.dto.SearchCondition;
import croundteam.cround.creator.service.CreatorService;
import croundteam.cround.creator.service.dto.CreatorSaveRequest;
import croundteam.cround.member.service.dto.LoginMember;
import croundteam.cround.security.token.support.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @PostMapping
    public ResponseEntity<Void> createCreator(
            @Login LoginMember member,
            @RequestBody CreatorSaveRequest creatorSaveRequest
    ) {
        String activityName = creatorService.createCreator(member, creatorSaveRequest);
        return ResponseEntity.created(URI.create("/api/creators/" + activityName)).build();
    }

    @GetMapping
    public ResponseEntity<Page<SearchCreatorResponse>> searchCreators(
            SearchCondition searchCondition
    ) {
        Page<SearchCreatorResponse> body = creatorService.searchCreatorsByCondition(searchCondition);
        return ResponseEntity.ok(body);
    }
}
