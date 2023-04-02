package com.example.userservice.model;

public class FollowRequest {
    private String followerUsername;
    private String followingUsername;

    public FollowRequest() {
    }

    public FollowRequest(String followerUsername, String followingUsername) {
        this.followerUsername = followerUsername;
        this.followingUsername = followingUsername;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }

    @Override
    public String toString() {
        return "FollowRequest{" +
                "followerUsername='" + followerUsername + '\'' +
                ", followingUsername='" + followingUsername + '\'' +
                '}';
    }
}
