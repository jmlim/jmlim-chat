package io.jmlim.chat.config.auth;

import com.google.common.collect.Lists;
import io.jmlim.chat.domain.user.Role;
import io.jmlim.chat.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountAdapter extends org.springframework.security.core.userdetails.User {

    private User user;

    public AccountAdapter(User user) {
        super(user.getEmail(), user.getPassword(), authorities(user.getRole()));
        this.user = user;
    }

    //private static Collection<? extends GrantedAuthority> authorities(Set<Role> roles) {
    private static Collection<? extends GrantedAuthority> authorities(Role role) {
        List<Role> roles = Lists.newArrayList(role);
        Set<SimpleGrantedAuthority> set =
                roles.stream()
                        .map(r -> new SimpleGrantedAuthority(r.getKey()))
                        .collect(Collectors.toSet());
        return set;
    }

    public String getName() {
        return user.getName();
    }

    public Long getId() {
        return user.getId();
    }
}