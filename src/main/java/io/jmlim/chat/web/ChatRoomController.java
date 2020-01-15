package io.jmlim.chat.web;

import io.jmlim.chat.domain.room.ChatRoom;
import io.jmlim.chat.service.ChatRoomService;
import io.jmlim.chat.web.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chat/chat-room")
    public String chatRoom(Model model) {
        return "/chat/chat-room-list";
    }

    @GetMapping("/chat/chat-room-detail/{roomId}")
    public String chatRoomDetail(Model model, @PathVariable Long roomId) {

        chatRoomService.findById(roomId);

        model.addAttribute("roomId", roomId);
        return "/chat/chat-room-detail";
    }

    /**
     * rest api 구분 필요..
     *
     * @return
     */
    @GetMapping(value = "/api/chat/chat-room")
    public @ResponseBody
    ResponseEntity<List<ChatRoomDto>> getChatRooms() {
        List<ChatRoomDto> all = chatRoomService.findAll();
        return ResponseEntity.ok(all);
    }

    /**
     * rest api 구분 필요..
     *
     * @param requestDto
     * @return
     */
    @PreAuthorize("hasRole('ROLE_POWERUSER')")
    @PostMapping(value = "/api/chat/chat-room")
    public @ResponseBody
    ResponseEntity<ChatRoom> save(@RequestBody ChatRoomDto requestDto) {
        // TODO: Validation 처리
        log.info("requestDto {} : ", requestDto);
        ChatRoom save = chatRoomService.save(requestDto);
        return ResponseEntity.ok(save);
    }
}
