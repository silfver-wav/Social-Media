package com.example.socialmediaserver.controller;

import com.example.socialmediaserver.model.ChatRoom;
import com.example.socialmediaserver.model.dtos.ChatRoomDTO;
import com.example.socialmediaserver.service.ChatRoomService;
import com.example.socialmediaserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/chat")
public class ChatController {

    @Autowired
    private ChatRoomService roomService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/create-room")
    public ResponseEntity<Object> getRoom(@RequestBody ChatRoomDTO roomDTO) {
        roomService.getRoom(roomDTO);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/create-room")
    public ResponseEntity<Object> createRoom(@RequestBody ChatRoom room) {
        roomService.createRoom(room);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/previous-messages/{sender}/{receiver}")
    public ResponseEntity<Object> getPreviousMessages(@PathVariable("sender") String sender, @PathVariable("receiver") String receiver) {
        return messageService.getMessages(sender, receiver);
    }
}
