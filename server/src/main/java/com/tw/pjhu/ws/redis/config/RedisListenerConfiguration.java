package com.tw.pjhu.ws.redis.config;

import com.tw.pjhu.ws.redis.RedisMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisListenerConfiguration {

    @Autowired
    private RedisMessageListener redisMessageListener;

    @Autowired
    private GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer;

    @Bean
    public MessageListenerAdapter messageListenerInGroupAdapter() {
        MessageListenerAdapter adapter = new MessageListenerAdapter(redisMessageListener);
        adapter.setDefaultListenerMethod("listenRedisMessageInGroup");
        adapter.setSerializer(genericJackson2JsonRedisSerializer);
        return adapter;
    }

    @Bean
    public MessageListenerAdapter messageListenerToUserAdapter() {
        MessageListenerAdapter adapter = new MessageListenerAdapter(redisMessageListener);
        adapter.setDefaultListenerMethod("listenRedisMessageToUser");
        adapter.setSerializer(genericJackson2JsonRedisSerializer);
        return adapter;
    }
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory rcf) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(rcf);
        container.addMessageListener(messageListenerInGroupAdapter(), new ChannelTopic("to-topic"));
        container.addMessageListener(messageListenerToUserAdapter(), new ChannelTopic("to-user"));
        return container;
    }
}
