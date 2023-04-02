package com.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Model class for user.
 */
@Entity
@Table(name = "User")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String password;
    private String email;
    @Column(unique = true)
    private String username;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "user_followers",
            joinColumns = { @JoinColumn(name = "follower_id") },
            inverseJoinColumns = { @JoinColumn(name = "following_id") })
    private List<User> followers = new ArrayList<>();

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "followers")
    private List<User> following = new ArrayList<>();

    @JsonCreator
    public User(UUID id, String firstName, String lastName, String password, String email, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public User(UUID id, String firstName, String lastName, String email, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }

    public User() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}