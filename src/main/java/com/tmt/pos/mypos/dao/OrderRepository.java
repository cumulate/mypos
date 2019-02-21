package com.tmt.pos.mypos.dao;

import com.tmt.pos.mypos.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
