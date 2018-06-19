package com.tw.pjhu.ws.handler;

import com.tw.pjhu.ws.model.ChatMessage;
import com.tw.pjhu.ws.model.GroupNotification;
import com.tw.pjhu.ws.model.UserNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationHandler {
    @Autowired
    public EventPublisher eventPublisher;

    public void handle(ChatMessage msg) {
        switch (msg.getType()) {
            case TOPIC:
                eventPublisher.publish(new GroupNotification(msg));
                return;
            case USER:
                eventPublisher.publish(new UserNotification(msg));
                return;
            default:
                //TODO exception need to be thrown
                return;
        }
    }
}
