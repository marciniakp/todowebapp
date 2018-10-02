package com.betapp.repositories;

import com.betapp.model.Customer;
import com.betapp.model.TODO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

    Customer findCustomerByName(String name);
    List<TODO> getTodosByName(String name);
}
