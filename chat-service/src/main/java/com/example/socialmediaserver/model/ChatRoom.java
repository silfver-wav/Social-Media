package com.example.socialmediaserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ChatRoom")
@ToString
public class ChatRoom {

    @Id
    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "room_members", joinColumns = @JoinColumn(name = "chatRoom_id"))
    @Column(name = "member")
    private List<String> members;

    @JsonCreator
    public ChatRoom() {}

    public ChatRoom(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.members = new ArrayList<>();
    }

    public ChatRoom(UUID id, String name, List<String> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void addMember(String member) {
        members.add(member);
    }

    public void removeMember(String member) {
        members.remove(member);
    }
}

