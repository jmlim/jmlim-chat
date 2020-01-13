package io.jmlim.chat.socket;

import io.jmlim.chat.config.auth.CurrentUser;
import io.jmlim.chat.config.auth.LoginUser;
import io.jmlim.chat.domain.chat.ChatParticipant;
import io.jmlim.chat.domain.chat.ChatParticipantRepository;
import io.jmlim.chat.socket.dto.ChatParticipantDto;
import io.jmlim.chat.socket.dto.WebSocketChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final ChatParticipantRepository chatParticipantRepository;

    /**
     * 대화 참여자 목록 호출
     *
     * @param currentUser
     * @return
     */
    @MessageMapping("/{roomId}/chat.callParticipants")
    @SendTo("/topic/chatting")
    public List<ChatParticipantDto> callParticipants(@LoginUser CurrentUser currentUser
            , @DestinationVariable Long roomId) {

        log.info("roomId : {} ", roomId);
        List<ChatParticipantDto> chatParticipantDtos = chatParticipantRepository.findByRoomId(1L)
                .stream().map(ChatParticipantDto::new).collect(Collectors.toList());
        return chatParticipantDtos;
    }

    /**
     * 메시지 전송
     *
     * @param webSocketChatMessage
     * @param currentUser
     * @return
     */
    @MessageMapping("/{roomId}/chat.sendMessage")
    @SendTo("/topic/chatting")
    public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage
            , @LoginUser CurrentUser currentUser
            , @DestinationVariable Long roomId) {
        log.info("roomId : {} ", roomId);
        webSocketChatMessage.setSender(currentUser.getName());
        return webSocketChatMessage;
    }

    /**
     * 새로운 유저 입장
     *
     * @param webSocketChatMessage
     * @param currentUser
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/{roomId}/chat.newUser")
    @SendTo("/topic/chatting")
    public WebSocketChatMessage addUser(@Payload WebSocketChatMessage webSocketChatMessage
            , @LoginUser CurrentUser currentUser
            , SimpMessageHeaderAccessor headerAccessor
            , @DestinationVariable Long roomId) {
        log.info("roomId : {} ", roomId);
        String username = currentUser.getName();
        String email = currentUser.getEmail();

        webSocketChatMessage.enter(username, email);
        Long userId = currentUser.getId();

        ChatParticipantDto chatParticipantDto = ChatParticipantDto.builder()
                .roomId(1L)
                .userId(userId)
                .username(username)
                .email(email).build();
        ChatParticipant savedParticipant = chatParticipantRepository.save(chatParticipantDto.toEntity());

        headerAccessor.getSessionAttributes().put("participantId", savedParticipant.getId());
        headerAccessor.getSessionAttributes().put("username", username);
        return webSocketChatMessage;
    }
}