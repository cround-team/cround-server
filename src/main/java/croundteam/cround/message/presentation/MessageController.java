package croundteam.cround.message.presentation;

import croundteam.cround.message.application.MessageService;
import croundteam.cround.message.application.dto.MessageSaveRequest;
import croundteam.cround.support.annotation.Login;
import croundteam.cround.support.vo.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
