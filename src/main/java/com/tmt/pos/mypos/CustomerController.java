package com.tmt.pos.mypos;

import com.tmt.pos.mypos.entities.Customer;
import com.tmt.pos.mypos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CustomerController {

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    @ResponseBody
    public List<Customer> all() {
        return customerService.getAllCustomers();
    }

}
