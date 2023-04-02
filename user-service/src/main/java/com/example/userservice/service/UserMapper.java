package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.model.UserProfileResponse;
import org.mapstruct.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Mapper
public class UserMapper {


    public UserProfileResponse fromUser(User user) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setInitials(getInitials(user.getFirstName() + " " + user.getLastName()));
        userProfileResponse.setName(user.getFirstName() + " " + user.getLastName());
        userProfileResponse.setUsername(user.getUsername());
        userProfileResponse.setEmail(user.getEmail());

        //create list for stats
        List<Map<String, Object>> stats = new ArrayList<>();

        //create map for followers stat
        Map<String, Object> followers = new HashMap<>();
        followers.put("label", "Followers");
        followers.put("value", user.getFollowers().size());

        //create map for following stat
        Map<String, Object> following = new HashMap<>();
        following.put("label", "Following");
        following.put("value", user.getFollowing().size());

        //add stat maps to stats list
        stats.add(followers);
        stats.add(following);

        userProfileResponse.setStats(stats);

        return userProfileResponse;
    }

    public List<UserProfileResponse> fromUsers(List<User> users) {
        return users.stream().map(this::fromUser).collect(Collectors.toList());
    }

    private String getInitials(String name) {
        String[] names = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String n : names) {
            initials.append(n.charAt(0));
        }
        return initials.toString();
    }
}
