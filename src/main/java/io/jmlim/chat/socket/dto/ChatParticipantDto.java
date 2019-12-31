package io.jmlim.chat.socket.dto;

import io.jmlim.chat.domain.chat.ChatParticipant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatParticipantDto {
    private Long roomId;
    private Long userId;
    private String email;
    private String username;

    @Builder
    public ChatParticipantDto(Long roomId, Long userId, String email, String username) {
        this.roomId = roomId;
        this.userId = userId;
        this.email = email;
        this.username = username;
    }

    public ChatParticipantDto(ChatParticipant chatParticipant) {
        this.roomId = chatParticipant.getRoomId();
        this.userId = chatParticipant.getUserId();
        this.email = chatParticipant.getEmail();
        this.username = chatParticipant.getUsername();
    }

    public ChatParticipant toEntity() {
        return ChatParticipant.builder()
                .roomId(roomId)
                .email(email)
                .userId(userId)
                .username(username)
                .build();
    }
}