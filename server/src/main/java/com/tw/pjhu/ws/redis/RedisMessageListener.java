package com.tw.pjhu.ws.redis;

import com.tw.pjhu.ws.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisMessageListener {

    @Value("${server.port}")
    private String port;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void listenRedisMessageInGroup (ChatMessage redisMsg) {
        log.debug(port +" receive message : " + redisMsg);
        simpMessagingTemplate.convertAndSend("/topic/public", redisMsg);
    }

    public void listenRedisMessageToUser (ChatMessage redisMsg) {
        log.debug(port +" receive message : " + redisMsg);
        simpMessagingTemplate.convertAndSendToUser(redisMsg.getReceiver(),"/queue/notification", redisMsg);
    }
}
