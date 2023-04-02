package com.example.userservice.repository;

import com.example.userservice.model.Following;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for following relation in the database.
 */
public interface FollowingRepository extends CrudRepository<Following, UUID> {
    Optional<Following> findByUser(String user);
}
