package com.tw.pjhu.ws.controller.kafka;

import com.google.gson.Gson;
import com.tw.pjhu.ws.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class KafkaSenderController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private Gson gson;

    @PostMapping("/kafka/send-message/{user}")
    public void sendToUserTopic (@PathVariable String user) {
        ChatMessage toUserMessage = new ChatMessage().builder()
                .content("send from kafka queue.")
                .sender(user)
                .type(ChatMessage.MessageType.CHAT)
                .build();
        kafkaTemplate.send("to-user", gson.toJson(toUserMessage));
    }

    @PostMapping("/kafka/send-message/topic")
    public void sendToTopicTopic () {
        ChatMessage toTopicMessage = new ChatMessage().builder()
                .content("send from kafka queue.")
                .sender("kafka broker")
                .type(ChatMessage.MessageType.CHAT)
                .build();
        kafkaTemplate.send("to-topic", gson.toJson(toTopicMessage));
    }

    @PostMapping("/kafka/send-message/all")
    public void sendAll () {
        ChatMessage toTopicMessage = new ChatMessage().builder()
                .content("send from kafka queue.")
                .sender("kafka broker")
                .type(ChatMessage.MessageType.CHAT)
                .build();
        kafkaTemplate.send("to-all", gson.toJson(toTopicMessage));
    }
}
