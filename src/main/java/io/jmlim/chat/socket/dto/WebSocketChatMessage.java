package io.jmlim.chat.socket.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
public class WebSocketChatMessage {
    private String type;
    private String content;
    @Setter
    private String sender;
    private String email;
    private LocalDateTime dateTime;

    public WebSocketChatMessage() {
        dateTime = LocalDateTime.now();
    }

    @Builder
    public WebSocketChatMessage(String type, String content, String sender, String email) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.email = email;
        this.dateTime = LocalDateTime.now();
    }

    public void enter(String sender, String email) {
        this.sender = sender;
        this.email = email;
        this.content = this.sender + " 님이 입장하였습니다.";
    }
}
