package com.example.socialmediaserver.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Model class for message.
 */
@Entity
@Table(name = "Message")
@ToString
public class Message {
    @Id
    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID id;

    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "sender", nullable = false)
    private String sender;
    @Column(name = "receiver", nullable = false)
    private String receiver;
    @Column(name = "room", nullable = false)
    private String room;
    @Column(name = "timeSent", nullable = false)
    private long timeStamp;

    @JsonCreator
    public Message() {}

    public Message(UUID id, String text, String sender, String receiver, String room, long timeStamp) {
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.room = room;
        this.timeStamp = timeStamp;
    }

    public Message(String receiver, String text, String sender) {
        this.receiver = receiver;
        this.text = text;
        this.sender = sender;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}