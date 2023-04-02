package com.socialmedia.logservice.interfaces;

import com.socialmedia.logservice.model.PersonalLog;
import com.socialmedia.logservice.model.PersonalLogRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for personal log.
 */
public interface IPersonalLogService {
    ResponseEntity<Object> add(PersonalLogRequest personalLogRequest);

    ResponseEntity<Object> getAllByUser(String username);

    ResponseEntity<Object> getFollowingPosts(String username);

}
