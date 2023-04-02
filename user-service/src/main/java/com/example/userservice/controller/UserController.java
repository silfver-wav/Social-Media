package com.example.userservice.controller;

import com.example.userservice.interfaces.IFollowingService;
import com.example.userservice.interfaces.IUserService;
import com.example.userservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest API for user.
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final IUserService userService;
    private final IFollowingService followingService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/register")
    public ResponseEntity<Object> register(@RequestBody UserRequest user) {
        return userService.register(user);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/login")
    public ResponseEntity<Object> login(@RequestBody UserRequest user) {
        return userService.login(user);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, path ="/follow")
    public ResponseEntity<Object> follow(@RequestBody FollowRequest followRequest) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("followRequest = " + followRequest.toString());

        return userService.follow(followRequest);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, path ="/unfollow")
    public ResponseEntity<Object> unfollow(@RequestBody FollowRequest followRequest) {
        return userService.unfollow(followRequest);
    }


    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username) {
        return userService.get(username);
    }


    @GetMapping(value = "getAll/{username}")
    public ResponseEntity<Object> getAllUsers(@PathVariable("username") String username) {
        return userService.getAll(username);
    }


    @GetMapping( "/getFollowing/{username}")
    public ResponseEntity<Object> getFollowing(@PathVariable("username") String username) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("getFollowing = " + username);

        return userService.getFollowing(username);
    }


    @GetMapping("/notfollowing/{username}")
    public ResponseEntity<Object> getNotFollowing(@PathVariable("username") String username) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Getting users that " + username + " is not following");

        return userService.getNotFollowing(username);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getFollowing2/{username}")
    public List<UserDTO> getFollowing2(@PathVariable("username") String username) {
        System.out.println("getFollow: "+username);

        return userService.getFollowing2(username);
    }
}