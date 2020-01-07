package io.jmlim.chat.config;

import com.hectorlopezfernandez.pebble.springsecurity.SpringSecurityExtension;
import com.mitchellbosecke.pebble.extension.Extension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PebbleConfig {
    @Bean
    public Extension springSecurityExtension() {
        return new SpringSecurityExtension();
    }
}
