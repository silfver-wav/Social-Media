package com.example.socialmediaserver.repository;

import com.example.socialmediaserver.model.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, UUID> {
    @Query("SELECT r FROM ChatRoom r WHERE :userNames MEMBER OF r.members GROUP BY r HAVING COUNT(DISTINCT r.members) = :numMembers")
    List<ChatRoom> findByMembers(@Param("userNames") List<String> userNames, @Param("numMembers") Long numMembers);
}
