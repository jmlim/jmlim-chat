package io.jmlim.chat.service;

import io.jmlim.chat.domain.chat.ChatParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatParticipantRepository chatParticipantRepository;

    @Transactional
    public void deleteByEmail(String email) {
        chatParticipantRepository.deleteByEmail(email);
    }
}
