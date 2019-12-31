package io.jmlim.chat.domain.chat;

import io.jmlim.chat.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class ChatParticipant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 임시, 아직 방이 따로 없음. 하나임..
    private Long roomId;
    private Long userId;
    private String email;
    private String username;

    @Builder
    public ChatParticipant(Long roomId, Long userId, String email, String username) {
        this.roomId = roomId;
        this.userId = userId;
        this.email = email;
        this.username = username;
    }
}