package com.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

public class UserResponse {

    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private List<UserResponse> followers;
    @JsonIgnore
    private List<UserResponse> following;

    public UserResponse(String name, String username, String email, List<UserResponse> followers, List<UserResponse> following) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.followers = followers;
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserResponse> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserResponse> followers) {
        this.followers = followers;
    }

    public List<UserResponse> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserResponse> following) {
        this.following = following;
    }
}
