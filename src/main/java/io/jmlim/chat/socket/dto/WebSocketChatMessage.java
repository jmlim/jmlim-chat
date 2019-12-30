package io.jmlim.chat.socket.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class WebSocketChatMessage {
    private String type;
    private String content;
    @Setter
    private String sender;

    @Builder
    public WebSocketChatMessage(String type, String content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
    }

    public void enter(String sender) {
        this.sender = sender;
        this.content = this.sender + " 님이 입장하였습니다.";
    }
}
