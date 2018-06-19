package com.tw.pjhu.ws.controller;

import com.tw.pjhu.ws.model.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Value("${server.port}")
    private String port;

    /**
     * 表示服务端可以接收客户端通过主题"/app/chat/send-message"发送过来的消息，
     * 客户端需要在主题"/topic/public"上监听并接收服务端发回的消息
     */
    @MessageMapping("/chat/send-message")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println("send message");
        return chatMessage;
    }

    @MessageMapping("/chat/add-user")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setSender(chatMessage.getSender()+ " connected in port:" + port);
        return chatMessage;
    }

}
