package com.example.userservice.interfaces;

import com.example.userservice.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service Interface for user.
 */
public interface IUserService {
    ResponseEntity<Object> register(UserRequest user);

    ResponseEntity<Object> login(UserRequest user);

    ResponseEntity<Object> get(String username);

    ResponseEntity<Object> getAll(String username);

    ResponseEntity<Object> follow(FollowRequest followRequest);

    ResponseEntity<Object> unfollow(FollowRequest followRequest);

    ResponseEntity<Object> getFollowing(String username);

    ResponseEntity<Object> getNotFollowing(String username);

    List<UserDTO> getFollowing2(String username);
}
