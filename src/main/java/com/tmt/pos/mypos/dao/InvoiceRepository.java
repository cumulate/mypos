package com.tmt.pos.mypos.dao;

import com.tmt.pos.mypos.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
