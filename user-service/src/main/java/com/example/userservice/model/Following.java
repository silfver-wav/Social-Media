package com.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Model class for following.
 */
@Entity
@Table(name = "Following")
public class Following {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID id;

    @Column(unique = true)
    String user;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    Set<User> users;

    @JsonCreator
    public Following(UUID id, String user, Set<User> users) {
        this.id = id;
        this.user = user;
        this.users = users;
    }

    public Following() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public String toString() {
        return "Following{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", users=" + users +
                '}';
    }
}
