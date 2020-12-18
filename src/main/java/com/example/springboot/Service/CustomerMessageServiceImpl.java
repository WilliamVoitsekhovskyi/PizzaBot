package com.example.springboot.Service;

import com.example.springboot.Domain.CustomerMessage;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerMessageServiceImpl implements CustomerMessageService {
    private static Map<Long, CustomerMessage> customerMessageRepo = new HashMap<>();

    @Override
    public void createCustomerMessage(CustomerMessage customerMessage) {
        customerMessageRepo.put(customerMessage.getId(), customerMessage);
    }
    @Override
    public void updateCustomerMessage(Long id, CustomerMessage customerMessage) {
        customerMessageRepo.remove(id);
        customerMessage.setId(id);
        customerMessageRepo.put(id, customerMessage);
    }
    @Override
    public void deleteCustomerMessage(Long id) {
        customerMessageRepo.remove(id);

    }
    @Override
    public Collection<CustomerMessage> getCustomerMessages() {
        return customerMessageRepo.values();
    }
    @Override
    public CustomerMessage getCustomerMessage(Long id){
        return  customerMessageRepo.get(id);
    }
    @Override
    public int getAmount(){
        return customerMessageRepo.size()-1;
    }
}
