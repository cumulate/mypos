package com.tmt.pos.mypos.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.Item")
@Table(name = "Item")
@EqualsAndHashCode(of = {"itemCode"})
public class Item {

    @Id
    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "quantity")
    private java.math.BigDecimal quantity;

    @Column(name = "price")
    private java.math.BigDecimal price;

    @Column(name = "tax_amount")
    private java.math.BigDecimal taxAmount;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_time")
    private Date creationTime;

    @Column(name = "modification_time")
    private Date modificationTime;
}
