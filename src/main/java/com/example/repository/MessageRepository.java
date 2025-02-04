package com.example.repository;
import com.example.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository <Message,Integer> {
    @Query(value = "SELECT * FROM message WHERE posted_by = :posted_by", nativeQuery = true)
    List<Message> findByPostedBy(@Param("posted_by") int posted_by);
    
}
