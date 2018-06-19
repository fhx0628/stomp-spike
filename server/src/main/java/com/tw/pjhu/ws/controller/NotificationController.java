package com.tw.pjhu.ws.controller;

import com.tw.pjhu.ws.handler.NotificationHandler;
import com.tw.pjhu.ws.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private NotificationHandler notificationHandler;

    @PostMapping("/send-notification")
    @ResponseBody
    public String despatchMessage(@RequestBody ChatMessage msg) {
        notificationHandler.handle(msg);
        return "SUCCESS";
    }
}
