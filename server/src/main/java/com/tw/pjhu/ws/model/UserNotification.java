package com.tw.pjhu.ws.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class UserNotification extends ChatMessage {

    public UserNotification(ChatMessage msg){
        super.setType(msg.getType());
        super.setContent(msg.getContent());
        super.setSender(msg.getSender());
        super.setReceiver(msg.getReceiver());
    }
}
