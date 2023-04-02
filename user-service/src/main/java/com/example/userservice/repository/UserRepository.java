package com.example.userservice.repository;

import com.example.userservice.model.User;
import com.example.userservice.model.UserDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for user relation in the database.
 */
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    /**
     * Retrieves a list of all the users that a particular user is following
     * @param follower user that we want to get the following list of
     * @return list of users that the follower user is following
     */
    List<User> findByFollowers(User follower);

    @Query("SELECT new User(u.id, u.firstName, u.lastName, u.email, u.username) FROM User u JOIN u.followers f ON f = :follower")
    List<User> findUsersBeingFollowedBy(@Param("follower") User follower);

    /**
     * Retrieves a list of all the users that a particular user is not following
     * @param following user that we want to get the not following list of
     * @return list of users that the follower user is not following
     */
    @Query("SELECT u FROM User u WHERE u NOT IN  :following")
    List<User> findUsersNotBeingFollowedBy(@Param("following") List<User> following);


    @Query("SELECT u FROM User u WHERE NOT u.username = :username")
    List<User> findAllUserExceptCurrentUser(@Param("username") String username);
}
