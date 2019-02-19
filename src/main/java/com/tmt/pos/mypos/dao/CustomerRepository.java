package com.tmt.pos.mypos.dao;

import com.tmt.pos.mypos.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
