package com.example.socialmediaserver.service;

import com.example.socialmediaserver.model.ChatRoom;
import com.example.socialmediaserver.model.dtos.ChatRoomDTO;
import com.example.socialmediaserver.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;


    public void getRoom(ChatRoomDTO roomDTO) {
        /*
        try {
            chatRoomRepository.findById()
        }

         */
    }

    public void createRoom(ChatRoom room) {
        // Create a new topic for the room
        try {
            chatRoomRepository.save(room);

            // return
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRoom(ChatRoom room) {
        // Delete the topic for the room
        try {
            chatRoomRepository.delete(room);

            //return
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMember(ChatRoom room, String member) {
        // Add a member to the room
        try {
            room.getMembers().add(member);
            chatRoomRepository.save(room);

            // return
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeMember(ChatRoom room, String member) {
        // Remove a member from the room
        try {
            room.getMembers().remove(member);
            chatRoomRepository.save(room);

            // return
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renameRoom(ChatRoom room, String name) {
        // Rename the room
        try {
            room.setName(name);
            chatRoomRepository.save(room);

            // return
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


