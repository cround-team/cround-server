package croundteam.cround.creator.controller;

import croundteam.cround.creator.service.CreatorService;
import croundteam.cround.creator.service.dto.CreatorSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Void> createCreator(
            @PathVariable Long memberId,
            @RequestBody CreatorSaveRequest creatorSaveRequest
    ) {
        String activityName = creatorService.createCreator(memberId, creatorSaveRequest);
        return ResponseEntity.created(URI.create("/api/creators/" + activityName)).build();
    }
}
