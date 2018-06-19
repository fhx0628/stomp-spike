package com.tw.pjhu.ws.controller;

import com.tw.pjhu.ws.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = getSessionId(headerAccessor);
        logger.info("session id got from SessionConnectedEvent is " + sessionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getUser().getName();
        if (null != username) {
            logger.info("User Disconnected : " + username);
            ChatMessage leaveMessage = new ChatMessage().builder()
                    .content("")
                    .sender(username)
                    .type(ChatMessage.MessageType.LEAVE)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", leaveMessage);
        }
    }

    private String getSessionId(StompHeaderAccessor headerAccessor) {
        String sessionId = "";
        Object simpConnectMessage = headerAccessor.getHeader("simpConnectMessage");
        if (simpConnectMessage instanceof Message) {
            Object simpSessionAttributes = ((Message) simpConnectMessage).getHeaders().get("simpSessionAttributes");
            if (simpSessionAttributes instanceof Map) {
                sessionId = ((Map) simpSessionAttributes).get("sessionId").toString();
            }
        }
        return sessionId;
    }
}
