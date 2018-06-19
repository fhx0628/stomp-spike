package com.tw.pjhu.ws.controller.rabbitmq;

import com.tw.pjhu.ws.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RabbitmqController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/rabbitmq/send-message/topic")
    public void sendRedisMessageInTopic(@RequestBody ChatMessage msg) {
        simpMessagingTemplate.convertAndSend("/topic/public", msg);
    }

    @PostMapping("/rabbitmq/send-message/user")
    public void sendRedisMessageInUser(@RequestBody ChatMessage msg) {
        simpMessagingTemplate.convertAndSendToUser(msg.getReceiver(),"/topic/public", msg);
    }
}
