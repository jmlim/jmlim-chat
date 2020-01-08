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
    private LocalDateTime dateTime;

    public WebSocketChatMessage() {
        dateTime = LocalDateTime.now();
    }

    @Builder
    public WebSocketChatMessage(String type, String content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.dateTime = LocalDateTime.now();
    }

    public void enter(String sender) {
        this.sender = sender;
        this.content = this.sender + " 님이 입장하였습니다.";
    }
}
