package croundteam.cround.message.presentation;

import croundteam.cround.message.application.MessageService;
import croundteam.cround.message.application.dto.FindMessageResponses;
import croundteam.cround.message.application.dto.MessageSaveRequest;
import croundteam.cround.message.application.dto.SearchMessageResponse;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.vo.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Long> saveMessage(
            @Login LoginMember loginMember,
            @RequestBody MessageSaveRequest messageSaveRequest
    ) {
        Long message = messageService.saveMessage(loginMember, messageSaveRequest);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<FindMessageResponses> findMessages(@Login LoginMember loginMember) {
        FindMessageResponses messages = messageService.findMessages(loginMember);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<SearchMessageResponse> findMessage(@PathVariable Long memberId, @Login LoginMember loginMember) {
        SearchMessageResponse messages = messageService.findMessage(memberId, loginMember);
        return ResponseEntity.ok(messages);
    }
}
