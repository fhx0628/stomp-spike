package com.tw.pjhu.ws.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class GroupNotification extends ChatMessage{

    public GroupNotification(ChatMessage msg){
        super.setType(msg.getType());
        super.setContent(msg.getContent());
        super.setSender(msg.getSender());
        super.setReceiver(msg.getReceiver());
    }
}
