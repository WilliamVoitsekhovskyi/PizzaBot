package com.example.springboot.Controller;

import com.example.springboot.Domain.CustomerMessage;
import com.example.springboot.Service.CustomerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerMessageServiceController {

    @Autowired
    CustomerMessageService customerMessageService;

    @RequestMapping(value = "/customerMessage")
    public ResponseEntity<Object> getCustomerMessage() {
        return new ResponseEntity<>(customerMessageService.getCustomerMessages(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customerMessage/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object>
    updateCustomerMessage(@PathVariable("id") Long id, @RequestBody CustomerMessage CustomerMessage) {

        customerMessageService.updateCustomerMessage(id, CustomerMessage);
        return new ResponseEntity<>("CustomerMessage is updated successsfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/customerMessage/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        customerMessageService.deleteCustomerMessage(id);
        return new ResponseEntity<>("CustomerMessage is deleted successsfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/customerMessage", method = RequestMethod.POST)
    public ResponseEntity<Object> createCustomerMessage(@RequestBody CustomerMessage CustomerMessage) {
        customerMessageService.createCustomerMessage(CustomerMessage);
        return new ResponseEntity<>("CustomerMessage is created successfully", HttpStatus.CREATED);
    }
}
