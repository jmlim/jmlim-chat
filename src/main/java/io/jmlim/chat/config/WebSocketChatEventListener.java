package io.jmlim.chat.config;

import io.jmlim.chat.socket.dto.WebSocketChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info(" -------------------> Received a new web socket connection : {}", event);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        log.info(":::::: username ------------> {} ", username);

        if (username != null) {
            WebSocketChatMessage chatMessage = WebSocketChatMessage.builder()
                    .type("Leave")
                    .sender(username)
                    .content(username + " 님이 퇴장하였습니다.").build();
            messagingTemplate.convertAndSend("/topic/chatting", chatMessage);
        }
    }
}
