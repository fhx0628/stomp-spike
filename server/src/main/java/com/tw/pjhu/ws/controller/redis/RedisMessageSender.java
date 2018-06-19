package com.tw.pjhu.ws.controller.redis;

import com.tw.pjhu.ws.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RedisMessageSender {
    @Autowired
    private final RedisTemplate redisTemplate;

    @Value("${server.port}")
    private String port;

    @PostMapping("/redis/send-message/topic")
    public void sendRedisMessageInTopic(@RequestBody ChatMessage msg) {
        redisTemplate.convertAndSend("to-topic", msg);
    }

    @PostMapping("/redis/send-message/user")
    public void sendRedisMessageToUser(@RequestBody ChatMessage msg) {
        redisTemplate.convertAndSend("to-user", msg);
    }
}
