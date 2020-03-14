package io.jmlim.chat.config;

import io.jmlim.chat.config.auth.LoginUserArgumentResolver;
import io.jmlim.chat.properties.BrokerRelayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
@EnableConfigurationProperties(BrokerRelayProperties.class)
public class WebSocketChatConfig implements WebSocketMessageBrokerConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    private final BrokerRelayProperties brokerRelayProperties;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //registry.addEndpoint("/websocketApp").withSockJS();
        registry.addEndpoint("/chat/chat-handler").withSockJS();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost(brokerRelayProperties.getRelayHost())
                .setRelayPort(brokerRelayProperties.getRelayPort())
                .setClientLogin(brokerRelayProperties.getClientLogin())
                .setClientPasscode(brokerRelayProperties.getClientPasscode());
    }
}
