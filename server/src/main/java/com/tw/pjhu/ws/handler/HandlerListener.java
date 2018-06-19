package com.tw.pjhu.ws.handler;

import com.tw.pjhu.ws.model.GroupNotification;
import com.tw.pjhu.ws.model.UserNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class HandlerListener {
    @Autowired
    private final RedisTemplate redisTemplate;

    @EventListener
    public void on(GroupNotification notice) {
        log.debug("Got notice for group :", notice);
        redisTemplate.convertAndSend("to-topic", notice);
    }

    @EventListener
    public void on(UserNotification notice) {
        log.debug("Got notice for user :", notice);
        redisTemplate.convertAndSend("to-user", notice);
    }

}
