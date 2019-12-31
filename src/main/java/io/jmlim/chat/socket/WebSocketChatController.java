package io.jmlim.chat.socket;

import io.jmlim.chat.config.auth.AccountAdapter;
import io.jmlim.chat.domain.chat.ChatParticipant;
import io.jmlim.chat.domain.chat.ChatParticipantRepository;
import io.jmlim.chat.socket.dto.ChatParticipantDto;
import io.jmlim.chat.socket.dto.WebSocketChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final ChatParticipantRepository chatParticipantRepository;

    @MessageMapping("/chat.callParticipants")
    @SendTo("/topic/chatting")
    public List<ChatParticipantDto> callParticipants() {
        List<ChatParticipantDto> chatParticipantDtos = chatParticipantRepository.findByRoomId(1L)
                .stream().map(ChatParticipantDto::new).collect(Collectors.toList());
        return chatParticipantDtos;
    }

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
        String username = accountAdapter.getName();
        webSocketChatMessage.enter(username);

        Long userId = accountAdapter.getId();
        String email = accountAdapter.getUsername();

        ChatParticipantDto chatParticipantDto = ChatParticipantDto.builder()
                .roomId(1L)
                .userId(userId)
                .username(username)
                .email(email).build();
        ChatParticipant savedParticipant = chatParticipantRepository.save(chatParticipantDto.toEntity());

        //TODO
        headerAccessor.getSessionAttributes().put("participantId", savedParticipant.getId());
        headerAccessor.getSessionAttributes().put("username", username);
        return webSocketChatMessage;
    }
}