package io.jmlim.chat.socket;

import io.jmlim.chat.config.auth.AccountAdapter;
import io.jmlim.chat.socket.dto.WebSocketChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chatting")
    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage, Authentication authentication) {
        // TODO
        AccountAdapter accountAdapter = (AccountAdapter) authentication.getPrincipal();
        webSocketChatMessage.setSender(accountAdapter.getName());
        return webSocketChatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/chatting")
    public WebSocketChatMessage addUser(@Payload WebSocketChatMessage webSocketChatMessage,
                                        Authentication authentication, SimpMessageHeaderAccessor headerAccessor) {
        // TODO
        AccountAdapter accountAdapter = (AccountAdapter) authentication.getPrincipal();
        String name = accountAdapter.getName();

        webSocketChatMessage.enter(name);
        headerAccessor.getSessionAttributes().put("username", name);
        return webSocketChatMessage;
    }
}