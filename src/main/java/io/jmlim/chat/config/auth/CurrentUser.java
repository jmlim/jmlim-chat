package io.jmlim.chat.config.auth;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public CurrentUser(Claims body) {
        this.id = Long.parseLong(Objects.toString(body.get("id")));
        this.name = Objects.toString(body.get("name"), null);
        this.email = Objects.toString(body.get("username"), null);
        List<String> bodyRoles = (List<String>) body.get("roles");
        this.role = bodyRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}