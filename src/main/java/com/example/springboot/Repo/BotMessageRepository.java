package com.example.springboot.Repo;

import com.example.springboot.Domain.BotMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BotMessageRepository extends CrudRepository<BotMessage, Integer> {
    List<BotMessage> findAll();
    boolean existsByContent(String content);
}
