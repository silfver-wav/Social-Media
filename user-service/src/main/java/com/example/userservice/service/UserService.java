package com.example.userservice.service;

import com.example.userservice.interfaces.IUserService;
import com.example.userservice.model.*;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for user.
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * Registers a new user.
     * @param user User to be registered.
     * @return ResponseEntity with status code and message.
     */
    @Override
    public ResponseEntity<Object> register(UserRequest user) {
        User u = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (u != null) {
            return new ResponseEntity<>("User with this username already exists", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());

        userRepository.save(newUser);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, "User registered successfully");
    }

    /**
     * Logs in a user.
     * @param user User to be logged in.
     * @return ResponseEntity with status code and message.
     */
    public ResponseEntity<Object> login(UserRequest user) {
        Optional<User> rs = userRepository.findByUsername(user.getUsername());
        if (rs.isEmpty())
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Username not found");

        User u = rs.get();
        if (!passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
        else {
            return ResponseHandler.generateResponse(HttpStatus.OK, "Login successful");
        }
    }

    /**
     * Handles a follow request by adding a user to the followers list of another user
     * @param followRequest request object that contains follower and following user username
     * @return a response indicating success or failure of the follow request
     */
    @Override
    public ResponseEntity<Object> follow(FollowRequest followRequest) {
        User follower = userRepository.findByUsername(followRequest.getFollowerUsername()).orElse(null);
        User following = userRepository.findByUsername(followRequest.getFollowingUsername()).orElse(null);
        if (follower == null || following == null) {
            return new ResponseEntity<>("Invalid user", HttpStatus.BAD_REQUEST);
        }
        follower.getFollowing().add(following);
        following.getFollowers().add(follower);
        userRepository.save(follower);
        userRepository.save(following);
        return ResponseHandler.generateResponse(HttpStatus.OK, "Followed successfully");
    }

    /**
     * Handles an unfollow request by removing a user from the followers list of another user
     * @param followRequest request object that contains follower and following user username
     * @return a response indicating success or failure of the unfollow request
     */
    @Override
    public ResponseEntity<Object> unfollow(FollowRequest followRequest) {
        User follower = userRepository.findByUsername(followRequest.getFollowerUsername()).orElse(null);
        User following = userRepository.findByUsername(followRequest.getFollowingUsername()).orElse(null);
        if (follower == null || following == null) {
            return new ResponseEntity<>("Invalid user", HttpStatus.BAD_REQUEST);
        }
        follower.getFollowing().remove(following);
        following.getFollowers().remove(follower);
        userRepository.save(follower);
        userRepository.save(following);
        return ResponseHandler.generateResponse(HttpStatus.OK, "Unfollowed successfully");
    }

    @Override
    public ResponseEntity<Object> getFollowing(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<User> following = user.getFollowing();
        List<UserProfileResponse> followingResponse = userMapper.fromUsers(following);

        System.out.println(followingResponse);

        // fix so it doesn't return the user's password
        return ResponseHandler.generateResponse(HttpStatus.OK, "Following list retrieved successfully",followingResponse);
    }

    @Override
    public ResponseEntity<Object> getNotFollowing(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<User> notFollowing = new ArrayList<>();
        if (user.getFollowing().isEmpty()) {
            System.out.println("User is not following anyone");
            notFollowing = userRepository.findAllUserExceptCurrentUser(username);
        } else {
            notFollowing = userRepository.findUsersNotBeingFollowedBy(user.getFollowing());
        }

        // if contains user, remove it
        if (notFollowing.contains(user)) {
            notFollowing.remove(user);
        }

        List<UserProfileResponse> userProfileResponse = userMapper.fromUsers(notFollowing);
        System.out.println("Users: " + userProfileResponse.toString());
        return ResponseHandler.generateResponse(HttpStatus.OK, "Not following list retrieved successfully", userProfileResponse);
    }

    @Override
    public List<UserDTO> getFollowing2(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            return null;
        }

        List<User> following = user.getFollowing();
        List<UserDTO> followingResponse = new ArrayList<>();
        for (User value : following) {
            UserDTO u = new UserDTO(value.getFirstName(), value.getLastName(), value.getEmail(), value.getUsername());
            followingResponse.add(u);
        }


        System.out.println(followingResponse);

        // fix so it doesn't return the user's password
        return followingResponse;
    }


    @Override
    public ResponseEntity<Object> get(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "User not found");
        }
        UserProfileResponse userProfileResponse = userMapper.fromUser(user);

        return ResponseHandler.generateResponse(HttpStatus.OK, "User retrieved successfully", userProfileResponse);
    }


    @Override
    public ResponseEntity<Object> getAll(String username) {
        List<User> users = (List<User>) userRepository.findAll();
        return ResponseHandler.generateResponse(HttpStatus.OK, "Ok", users);
    }

}

