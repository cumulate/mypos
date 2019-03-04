package com.tmt.pos.mypos.service;

import com.tmt.pos.mypos.dao.CustomerRepository;

import com.tmt.pos.mypos.dao.UsersRepository;
import com.tmt.pos.mypos.entities.Customer;
import com.tmt.pos.mypos.entities.User;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Customer> getAll(Pageable pageable) {

        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage;
    }


    public Customer getOne(String customerCode) {
        log.info("fetching  customer -> " + customerCode);
        return customerRepository.findById(customerCode).get();
    }


    public Customer add(Customer customer) {
        log.info("Creating customer");
        String customerCode = generateCustomerCode(customer.getFirstName(), customer.getLastName());
        customer.setCustomerCode(customerCode);
        Customer c = customerRepository.save(customer);
        log.info("Customer saved successfully!!");
        return c;


    }

    private String generateCustomerCode(String firstName, String lastName) {
        String customerCode = StringUtils.truncate(firstName, 4) + "_" + StringUtils.truncate(lastName, 4);

        int i = 1;
        String suffix = "";
        while (customerRepository.existsById(customerCode + suffix)) {
            suffix = "" + i++;
        }
        return customerCode + suffix;
    }

    public Customer update(Customer customer) {
        log.info("updating customer");
        Customer c = customerRepository.save(customer);
        return c;
    }

    public void delete(String customerCode) {
        customerRepository.deleteById(customerCode);

    }

    public Page<User> getSalesMan(String firstOrLastName, Pageable page) {
        String name = String.format("%%%s%%", firstOrLastName);
        return userRepository.findByLastNameLikeOrFirstNameLike(name, name, page);
    }
}
