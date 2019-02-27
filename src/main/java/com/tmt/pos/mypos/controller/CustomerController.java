package com.tmt.pos.mypos.controller;

import com.tmt.pos.mypos.entities.Customer;
import com.tmt.pos.mypos.rest.dto.PaginatedResult;
import com.tmt.pos.mypos.rest.transformer.PageTransformer;
import com.tmt.pos.mypos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<PaginatedResult<Customer>> all(Pageable page) {

        Page<Customer> customerPage = customerService.getAllCustomers(page);

        return ResponseEntity.ok(PageTransformer.transformOutput(customerPage));
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getOne(@PathVariable(name = "id") String customerCode) {
        return ResponseEntity.ok(customerService.getOne(customerCode));
    }

}
