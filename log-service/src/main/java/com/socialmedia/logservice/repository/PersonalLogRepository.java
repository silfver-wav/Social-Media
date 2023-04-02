package com.socialmedia.logservice.repository;

import com.socialmedia.logservice.ValueObject.User;
import com.socialmedia.logservice.model.PersonalLog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for personal log relation in the database.
 */
public interface PersonalLogRepository extends CrudRepository<PersonalLog, UUID> {
    List<PersonalLog> findByUsername(String username);
    List<PersonalLog> findByUsernameInOrderByCreatedAtDesc(List<User> authors);
    List<PersonalLog> findAllByUsername(String userName);
}
