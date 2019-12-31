package io.jmlim.chat.config;

import io.jmlim.chat.domain.chat.ChatParticipantRepository;
import io.jmlim.chat.socket.dto.WebSocketChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    private final ChatParticipantRepository chatParticipantRepository;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info(" -------------------> Received a new web socket connection : {}", event);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Long participantId = (Long) headerAccessor.getSessionAttributes().get("participantId");
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        log.info(":::::: username ------------> {} ", username);

        if (Objects.nonNull(username)) {
            WebSocketChatMessage chatMessage = WebSocketChatMessage.builder()
                    .type("Leave")
                    .sender(username)
                    .content(username + " 님이 퇴장하였습니다.").build();

            chatParticipantRepository.deleteById(participantId);
            messagingTemplate.convertAndSend("/topic/chatting", chatMessage);
        }
    }
}
