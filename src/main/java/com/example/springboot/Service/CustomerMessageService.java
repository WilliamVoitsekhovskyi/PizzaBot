package com.example.springboot.Service;

import com.example.springboot.Domain.CustomerMessage;
import com.example.springboot.Domain.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CustomerMessageService{
    void createCustomerMessage(CustomerMessage customerMessage);
    void updateCustomerMessage(Long id, CustomerMessage customerMessage);
    void deleteCustomerMessage(Long id);
    CustomerMessage getCustomerMessage(Long id);
    int getAmount();
    Collection<CustomerMessage> getCustomerMessages();
}
