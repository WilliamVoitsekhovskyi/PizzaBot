package com.example.springboot.Service;

import com.example.springboot.Domain.BotResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class BotResponseServiceImpl implements BotResponseService {
    private static Map<Long, BotResponse> BotResponseRepo = new HashMap<>();

    @Override
    public void createBotResponse(BotResponse BotResponse) {
        BotResponseRepo.put(BotResponse.getId(), BotResponse);
    }
    @Override
    public Collection<BotResponse> getBotResponses() {
        return BotResponseRepo.values();
    }
    @Override
    public BotResponse getBotResponse(Long id){
        return  BotResponseRepo.get(id);
    }
}

