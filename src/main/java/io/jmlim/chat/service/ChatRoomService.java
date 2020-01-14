package io.jmlim.chat.service;

import io.jmlim.chat.domain.room.ChatRoom;
import io.jmlim.chat.domain.room.ChatRoomRepository;
import io.jmlim.chat.web.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public List<ChatRoomDto> findAll() {
        return chatRoomRepository.findAll()
                .stream()
                .map(ChatRoomDto::new) // posts -> new PostsListResponseDto(posts)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatRoom save(ChatRoomDto requestDto) {
        ChatRoom chatRoom = requestDto.toEntity();
        return chatRoomRepository.save(chatRoom);
    }
}
