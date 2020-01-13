package io.jmlim.chat.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatRoomController {

    @GetMapping("/chat/chat-room")
    public String chatRoom(Model model) {
        return "/chat/chat-room-list";
    }

    @GetMapping("/chat/chat-room-detail")
    public String chatRoomDetail(Model model) {
        return "/chat/chat-room-detail";
    }
}
