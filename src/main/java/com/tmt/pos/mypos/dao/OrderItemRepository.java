package com.tmt.pos.mypos.dao;

import com.tmt.pos.mypos.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
