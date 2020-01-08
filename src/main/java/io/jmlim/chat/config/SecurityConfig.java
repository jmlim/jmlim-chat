package io.jmlim.chat.config;

import io.jmlim.chat.domain.user.Role;
import io.jmlim.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/chat/chat-room").hasRole(Role.USER.name())// URL 별 권한관리를 설정하는 옵션의 시작점. (이게 선언되어야 antMatchers 옵션 사용가능
                //.antMatchers("/**")//.hasRole(Role.USER.name()) // USER 권한 가진사람만 열람가능
                .anyRequest().permitAll()
                .and().formLogin().loginPage("/sign/signin")
                //.failureForwardUrl("/sign/signin?error=1")
                .and()
                .logout().logoutSuccessUrl("/sign/signin");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
