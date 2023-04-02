package com.example.userservice.service;

import com.example.userservice.interfaces.IFollowingService;
import com.example.userservice.model.Following;
import com.example.userservice.model.User;
import com.example.userservice.repository.FollowingRepository;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for following.
 */
@Service
@RequiredArgsConstructor
public class FollowingService implements IFollowingService {

    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;

    /**
     * Follow a user
     * @param user_to_follow
     * @param user User
     * @return HTTP status OK if succeeded, otherwise HTTP status BAD_REQUEST.
     */
    @Override
    public ResponseEntity<Object> followUser(String user_to_follow, String user) {
        Optional<User> utf = userRepository.findByUsername(user_to_follow);
        Optional<User> u = userRepository.findByUsername(user);
        if (utf.isEmpty() || u.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "User does not exist");

        Optional<Following> rs = followingRepository.findByUser(user);
        if (rs.isEmpty()) return createFollowing(user, utf.get());

        Following following = rs.get();
        following.addUser(utf.get());
        followingRepository.save(following);

        return ResponseHandler.generateResponse(HttpStatus.OK, user + " is now following " + user_to_follow);
    }


    /**
     * Get all the users user follows from the database.
     * @param username
     * @return HTTP status OK if succeeded, otherwise HTTP status BAD_REQUEST.
     */
    @Override
    public ResponseEntity<Object> getAllFriends(String username) {
        Optional<Following> rs = followingRepository.findByUser(username);

        if (rs.isEmpty()) return ResponseHandler.generateResponse(HttpStatus.OK, "People "+username+" is following",new Object());

        Following following = rs.get();
        System.out.println(following.toString());
        return ResponseHandler.generateResponse(HttpStatus.OK, "People "+username+" is following",following.getUsers());
    }

    @Override
    public Following getFollowing(String username) {
        Optional<Following> rs = followingRepository.findByUser(username);

        if (rs.isEmpty()) return null;

        Following following = rs.get();
        System.out.println(following.toString());
        return following;
    }

    private ResponseEntity<Object> createFollowing(String user, User user_to_follow) {
        Following following = new Following();
        following.setUser(user);
        Set<User> users = new HashSet<>();
        users.add(user_to_follow);
        following.setUsers(users);
        followingRepository.save(following);
        return ResponseHandler.generateResponse(HttpStatus.OK, user+" is now following "+user_to_follow.getUsername());
    }
}
