package org.jmlim.chat.config;

import org.jmlim.chat.websocket.handler.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebMvc
@EnableWebSocket
@ComponentScan(basePackages = { "org.jmlim.chat" })
@ImportResource({ "classpath*:chat-context.xml" })
public class WebConfig extends WebMvcConfigurerAdapter implements
		WebSocketConfigurer {

	/**
	 * @see org.springframework.web.socket.config.annotation.WebSocketConfigurer#registerWebSocketHandlers(org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry)
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		registry.addHandler(chatWebSocketHandler(), "/chat/chat-handler")
				.addInterceptors(new HttpSessionHandshakeInterceptor());

		/*
		 * registry.addHandler(echoWebSocketHandler(), "/sockjs/chat-handler")
		 * .withSockJS();
		 */
		/*
		 * registry.addHandler(echoWebSocketHandler(), "/sockjs/echo-issue4")
		 * .withSockJS().setHttpMessageCacheSize(20000);
		 */
	}

	@Bean
	public WebSocketHandler chatWebSocketHandler() {
		return new ChatWebSocketHandler();
	}

	/*
	 * @Bean public DefaultEchoService echoService() { return new
	 * DefaultEchoService(); }
	 */

	// Allow serving HTML files through the default Servlet

	/**
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
	 */
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
