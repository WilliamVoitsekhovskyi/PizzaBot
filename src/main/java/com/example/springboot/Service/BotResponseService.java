package com.example.springboot.Service;

import com.example.springboot.Domain.BotResponse;
import com.example.springboot.Domain.BotResponse;

import java.util.Collection;

public interface BotResponseService {
        void createBotResponse(BotResponse BotResponse);
        BotResponse getBotResponse(Long id);
        Collection<BotResponse> getBotResponses();

}
