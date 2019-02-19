package com.tmt.pos.mypos.service;

import com.tmt.pos.mypos.dao.CustomerRepository;
import com.tmt.pos.mypos.entities.Customer;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {
        log.info("Saving customer");
        customerRepository.save(customer);

        log.info("Customer saved successfully!!");
    }
}
