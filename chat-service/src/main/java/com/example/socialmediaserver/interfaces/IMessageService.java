package com.example.socialmediaserver.interfaces;

import com.example.socialmediaserver.model.Message;
import org.springframework.http.ResponseEntity;

/**
 * Service Interface for message.
 */
public interface IMessageService {

    ResponseEntity<Object> saveMessage(Message msg);

    ResponseEntity<Object> getMessages(String sender, String receiver);
}
