package com.example.springboot.Controller;

import com.example.springboot.Domain.BotResponse;
import com.example.springboot.Domain.CustomerMessage;
import com.example.springboot.OrderReciever.Order;
import com.example.springboot.Service.BotResponseService;
import com.example.springboot.Service.CustomerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class BotResponseController {
    @Autowired
    Order order = new Order();

    @Autowired
    CustomerMessageService customerMessageService;

    @Autowired
    BotResponseService botResponseService;

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<Object> getResponse(){
        CustomerMessage customerMessage = customerMessageService.getCustomerMessage((long) customerMessageService.getAmount());
        BotResponse botResponse = new BotResponse(counter.incrementAndGet(), customerMessage.getContent(), order);
        botResponseService.createBotResponse(botResponse);
        return new ResponseEntity<>(botResponse, HttpStatus.OK);
    }

    @RequestMapping(value="/allBotResponses", method=RequestMethod.GET)
    public ResponseEntity<Object> getResponses(){
        return new ResponseEntity<>(botResponseService.getBotResponses(), HttpStatus.OK);
    }



}