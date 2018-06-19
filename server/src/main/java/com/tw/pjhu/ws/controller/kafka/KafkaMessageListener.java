package com.tw.pjhu.ws.controller.kafka;

import com.google.gson.Gson;
import com.tw.pjhu.ws.controller.WebSocketEventListener;
import com.tw.pjhu.ws.model.ChatMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private Gson gson;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    @KafkaListener(topics = {"to-user"})
    public void notifyInUser(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        Object message = kafkaMessage.get();
        if (kafkaMessage.isPresent()) {
            logger.info("log kafka record -----" + record);
            logger.info("get kafka message ------- " + message);
            ChatMessage chatMessage = gson.fromJson(message.toString(), ChatMessage.class);
            sendToSepicfiedUser(chatMessage);
        }
    }

    @KafkaListener(topics = {"to-topic"})
    public void notifyInTopic(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        Object message = kafkaMessage.get();
        if (kafkaMessage.isPresent()) {
            ChatMessage chatMessage = gson.fromJson(message.toString(), ChatMessage.class);
            chatMessage.setContent("notification for users in topic group.");
            chatMessage.setSender("notification from notifyInTopic method.");
            logger.info("log kafka record -----" + record);
            logger.info("get kafka message ------- " + message);
            simpMessagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

    @KafkaListener(topics = {"to-all"})
    public void notifyAll(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        Object message = kafkaMessage.get();
        if (kafkaMessage.isPresent()) {
            ChatMessage chatMessage = gson.fromJson(message.toString(), ChatMessage.class);
            chatMessage.setSender("notification from notifyAll method.");
            logger.info("log kafka record -----" + record);
            logger.info("get kafka message ------- " + message);
            chatMessage.setContent("notification for users in topic 'public'.");
            simpMessagingTemplate.convertAndSend("/topic/public", chatMessage);
            chatMessage.setContent("notification for users in topic 'queue'.");
            simpMessagingTemplate.convertAndSend("/topic/queue", chatMessage);
        }
    }

    private void sendToSepicfiedUser(ChatMessage chatMessage) {
        for (SimpUser user : userRegistry.getUsers()) {
            if (user.getName().equals(chatMessage.getSender())){
                logger.info("user name ---" + user);
                chatMessage.setContent("notification for " + user.getName());
                chatMessage.setSender("notification from notifyInUser method.");
                simpMessagingTemplate.convertAndSend("/topic/" + user.getName() + "/public", chatMessage);
                return;
            }
        }
        //TODO need method to handle this situation
    }
}
