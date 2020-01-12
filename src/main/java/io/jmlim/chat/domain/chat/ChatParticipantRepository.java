package io.jmlim.chat.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    List<ChatParticipant> findByRoomId(Long roomId);

    //List<ChatParticipant> findByUserId(Long userId);

    void deleteByEmail(String username);
}
