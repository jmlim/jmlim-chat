package io.jmlim.chat.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    EVERYONE("ROLE_EVERYONE", "모두"),
    USER("ROLE_USER","일반 사용자"),
    POWERUSER("ROLE_POWERUSER", "파워유저");

    private final String key;
    private final String title;
}
