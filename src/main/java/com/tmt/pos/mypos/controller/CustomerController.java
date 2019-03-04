package com.tmt.pos.mypos.controller;

import com.tmt.pos.mypos.entities.Customer;
import com.tmt.pos.mypos.entities.User;
import com.tmt.pos.mypos.rest.dto.PaginatedResult;
import com.tmt.pos.mypos.rest.transformer.PageTransformer;
import com.tmt.pos.mypos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<PaginatedResult<Customer>> all(Pageable page) {

        Page<Customer> customerPage = customerService.getAll(page);
        return ResponseEntity.ok(PageTransformer.transformOutput(customerPage));
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Customer> one(@PathVariable(name = "id") String customerCode) {
        return ResponseEntity.ok(customerService.getOne(customerCode));
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.add(customer));
    }


    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Customer> update(@PathVariable(name = "id") String customerCode, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.update(customer));
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String customerCode) {
        customerService.delete(customerCode);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/salesMan")
    @ResponseBody
    public ResponseEntity<List<User>> salesMan(@RequestParam("q") String firstOrLastName, Pageable page) {

        //Pageable page = PageRequest.of(0, 100, Sort.by(Sort.Order.asc("firstName"))); //the default behavior

        Page<User> users = customerService.getSalesMan(firstOrLastName, page);


        return ResponseEntity.ok(users.getContent());
    }

}
