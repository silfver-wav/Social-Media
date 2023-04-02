package com.example.socialmediaserver.controller;

import com.example.socialmediaserver.model.Message;
import com.example.socialmediaserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutionException;

@Controller
public class ChatSocket {

    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Socket for private chat and post endpoint for message.
     * @param message Message body
     */
    @MessageMapping("/private-message")
    public void sendMessage(Message message){
        //messageService.saveMessage(message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(),"/private",message);
    }


    /**
     * Socket for group chat and post endpoint for message.
     * @param message Message body
     */
    @MessageMapping("/group-message")
    public void groupMessage(Message message) {
        // Spara i databasen
        String roomName = message.getRoom();

        //String topic = roomService.getTopic(roomName);
        // Create a KafkaProducer instance using the KafkaProducerConfig bean
        //KafkaProducer<String, String> producer = kafkaProducerConfig.kafkaProducer();
        // Send the message to the topic
        //producer.send(new ProducerRecord<>(topic, message.toString()));
    }

}


