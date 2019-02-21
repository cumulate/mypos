package com.tmt.pos.mypos.controller;

import com.tmt.pos.mypos.entities.Customer;
import com.tmt.pos.mypos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    CustomerService customerService;

    @GetMapping
    @ResponseBody
    public List<Customer> all() {
        return customerService.getAllCustomers();
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getOne(@PathVariable(name = "id") String customerCode) {
        return ResponseEntity.ok(customerService.getOne(customerCode));
    }

}
