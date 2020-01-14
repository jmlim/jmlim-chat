package io.jmlim.chat.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatRoomController {

    @GetMapping("/chat/chat-room")
    public String chatRoom(Model model) {
        return "/chat/chat-room-list";
    }

    @GetMapping("/chat/chat-room-detail/{roomId}")
    public String chatRoomDetail(Model model, @PathVariable Long roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/chat-room-detail";
    }
}
