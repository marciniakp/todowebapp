package com.betapp.services;

import com.betapp.model.Customer;
import com.betapp.model.TODO;
import com.betapp.repositories.CustomerRepository;
import com.betapp.utils.WiaSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    public CustomerService() {
    }

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer register(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomer(String username) {
        return customerRepository.findCustomerByName(username);
    }

    public List<TODO> getTodos() {
        if(WiaSession.get().isAuthenticated()) {
            return getCustomer(WiaSession.get().getPlayer().getName()).getTodos();
        } else {
            return new ArrayList<>();
        }
    }
     public List<TODO> getDoneTodos() {
        return getTodos().stream().filter(TODO::isDone).collect(Collectors.toList());
    }
     public List<TODO> getNotDoneTodos() {
        return getTodos().stream().filter(t->!t.isDone()).collect(Collectors.toList());
    }


}
