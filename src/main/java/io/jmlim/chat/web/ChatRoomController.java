package io.jmlim.chat.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Administrator
 */
@Controller
public class ChatRoomController {

    @GetMapping("/chat/chat-room")
    public String chatRoom(Model model) {
        return "/chat/chat-room";
    }
}
