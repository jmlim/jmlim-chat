package io.jmlim.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * 스프링 부트 최신버전에 추가.
         * prefix에 따라 적절한 인코딩 사용.
         */
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
