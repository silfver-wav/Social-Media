package com.example.socialmediaserver.service;

import com.example.socialmediaserver.interfaces.IMessageService;
import com.example.socialmediaserver.model.Message;
import com.example.socialmediaserver.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class for message.
 */
@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;

    /**
     * Create a new message and saves it in the database
     * @param msg Message body
     * @return HTTP status CREATED if succeeded, otherwise HTTP status BAD_REQUEST.
     */
    @Override
    public ResponseEntity<Object> saveMessage(Message msg) {
        try {
            if (msg.getText() == null || msg.getReceiver() == null || msg.getSender() == null)
                return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "All fields required");

            msg.setId(UUID.randomUUID());
            msg.setTimeStamp(System.currentTimeMillis());
            messageRepository.save(msg);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, "Successfully created sent message");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "No user provided");
        }
    }

    /**
     * Gets all messages for a specific chat from the database.
     * @param sender
     * @param receiver
     * @return HTTP status OK if succeeded, otherwise HTTP status BAD_REQUEST.
     */
    @Override
    public ResponseEntity<Object> getMessages(String sender, String receiver) {
        List<Message> messages = new ArrayList<>();
        Iterable<Message> rs = messageRepository.findAllBySenderAndReceiverOrReceiverAndSender(sender, receiver, sender, receiver);

        for (Message message : rs) {
            messages.add(message);
        }

        return ResponseHandler.generateResponse(HttpStatus.OK, "Ok", messages);
    }

}
