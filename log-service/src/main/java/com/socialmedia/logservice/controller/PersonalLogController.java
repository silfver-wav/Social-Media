package com.socialmedia.logservice.controller;

import com.socialmedia.logservice.interfaces.IPersonalLogService;
import com.socialmedia.logservice.model.PersonalLog;
import com.socialmedia.logservice.model.PersonalLogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/personal_log")
public class PersonalLogController {
    private final IPersonalLogService personalLogService;


    @GetMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get(@PathVariable("username") String username) {
        return personalLogService.getAllByUser(username);
    }


    @GetMapping(value = "getFollowingPosts/{username}")
    public ResponseEntity<Object> getFollowingPosts(@PathVariable("username") String username) {
        System.out.println("username: "+username);
        return personalLogService.getFollowingPosts(username);
    }



    @RequestMapping(method = RequestMethod.POST, path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@RequestBody PersonalLogRequest personalLogRequest) {
        return personalLogService.add(personalLogRequest);
    }

}
