package com.example.userservice.interfaces;

import com.example.userservice.model.Following;
import org.springframework.http.ResponseEntity;

/**
 * Service Interface for following.
 */
public interface IFollowingService {
    ResponseEntity<Object> followUser(String user_to_follow, String user);
    ResponseEntity<Object> getAllFriends(String username);

    Following getFollowing(String username);
}
