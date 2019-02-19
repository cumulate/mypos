package com.tmt.pos.mypos.dao;

import com.tmt.pos.mypos.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
