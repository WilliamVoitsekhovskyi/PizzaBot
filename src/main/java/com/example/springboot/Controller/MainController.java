package com.example.springboot.Controller;

import com.example.springboot.Domain.BotMessage;
import com.example.springboot.Domain.ChatMessage;
import com.example.springboot.OrderReceiver;
import com.example.springboot.Repo.BotMessageRepository;
import com.example.springboot.Repo.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
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
    public String greetingForm(@ModelAttribute ChatMessage chatMessage, Model model) {
        model.addAttribute("botMessageTime", chatMessage.getTime());
        model.addAttribute("chatMessages", chatMessageRepository.findAll());
        model.addAttribute("botMessages", botMessageRepository.findAll());
        model.addAttribute("messages", messages);
        return "chat";
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute ChatMessage chatMessage, Model model) {
        model.addAttribute("chatMessages", chatMessageRepository.findAll());
        chatMessageRepository.save(chatMessage);
        BotMessage botMessage = new BotMessage();
        botMessage.setContent(orderReceiver.printOrder(chatMessage.getContent()));
        botMessageRepository.save(botMessage);
        messages.put(chatMessage, botMessage);
        model.addAttribute("botMessage", botMessage.getContent());
        model.addAttribute("botMessageTime", chatMessage.getTime());
        return "redirect:/";
    }

}