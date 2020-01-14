package io.jmlim.chat.service;

import io.jmlim.chat.config.auth.AccountAdapter;
import io.jmlim.chat.domain.user.User;
import io.jmlim.chat.domain.user.UserRepository;
import io.jmlim.chat.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserSaveRequestDto requestDto) {
        User user = requestDto.toEntity();

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        return userRepository.save(user).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new AccountAdapter(user);
    }
}
