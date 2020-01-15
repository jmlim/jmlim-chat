package io.jmlim.chat.exception.chat;

import lombok.Getter;

@Getter
public class ChatRoomNotFoundException extends RuntimeException {
    private Long requestRoomId;

    public ChatRoomNotFoundException(Long requestRoomId) {
        super();
        this.requestRoomId = requestRoomId;
    }
}
