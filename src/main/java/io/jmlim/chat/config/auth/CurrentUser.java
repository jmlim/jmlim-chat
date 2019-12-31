package io.jmlim.chat.config.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@NoArgsConstructor
@Getter
public class CurrentUser {
    private Long id;
    private String name;
    private String email;
    private Collection<GrantedAuthority> role;

    public CurrentUser(AccountAdapter accountAdapter) {
        this.id = accountAdapter.getId();
        this.name = accountAdapter.getName();
        this.email = accountAdapter.getUsername();
        this.role = accountAdapter.getAuthorities();
    }
}