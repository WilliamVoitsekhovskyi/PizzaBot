package com.example.springboot.Repo;

import com.example.springboot.Domain.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer> {
    List<ChatMessage> findAll();
    boolean existsByContent(String content);
}
