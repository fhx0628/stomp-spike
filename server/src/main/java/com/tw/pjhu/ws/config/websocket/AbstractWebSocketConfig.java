package com.tw.pjhu.ws.config.websocket;

import com.tw.pjhu.ws.config.websocket.interceptor.AddSessionIdToWSMessageInterceptor;
import com.tw.pjhu.ws.config.websocket.interceptor.RegisterUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class AbstractWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
            config.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("localhost") // broker host
                .setRelayPort(61613) // broker port
//                .setClientLogin("guest")
//                .setClientPasscode("guest")
                .setSystemLogin("admin")
                .setSystemPasscode("admin")
                ;
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new AddSessionIdToWSMessageInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(createUserInterceptor());
    }

    @Bean
    public RegisterUserInterceptor createUserInterceptor() {
        return new RegisterUserInterceptor();
    }

//    @Bean
//    public UserSessionRegistry getUserSessionRegistry() {
//        return new UserSessionRegistry(redisConnectionFactory);
//    }

}
