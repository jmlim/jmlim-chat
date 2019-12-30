package io.jmlim.chat.web.dto;

import io.jmlim.chat.domain.user.Role;
import io.jmlim.chat.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String name;
    private String email;
    private String password;
    private String picture;

    @Builder
    public UserSaveRequestDto(String name, String email, String password, String picture) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(Role.USER)
                .picture(picture)
                .build();
    }
}
