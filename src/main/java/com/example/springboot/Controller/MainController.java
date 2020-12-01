package com.example.springboot.Controller;

import com.example.springboot.Domain.BotMessage;
import com.example.springboot.Domain.ChatMessage;
import com.example.springboot.OrderReciever.OrderReceiver;
import com.example.springboot.Repo.BotMessageRepository;
import com.example.springboot.Repo.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    OrderReceiver orderReceiver = new OrderReceiver();

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private BotMessageRepository botMessageRepository;

    Map<ChatMessage, BotMessage> messages = new LinkedHashMap<>();

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String chat(@ModelAttribute ChatMessage chatMessage, Model model) {
        model.addAttribute("messages", messages);
        return "chat";
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String chatReceiveMessage(@ModelAttribute ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);

        BotMessage botMessage = new BotMessage();
        botMessage.setContent(orderReceiver.getResponse(chatMessage.getContent()));
        botMessageRepository.save(botMessage);

        messages.put(chatMessage, botMessage);

        return "redirect:/";
    }

}