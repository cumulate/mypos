package com.tmt.pos.mypos.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.OrderItem")
@Table(name = "OrderItem")
@ToString(exclude = {"order"})
@EqualsAndHashCode(of = {"orderItemId"})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "order_number", updatable = false, insertable = false)
    private String orderNumber;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "net_amount")
    private java.math.BigDecimal netAmount;

    @Column(name = "tax_amount")
    private java.math.BigDecimal taxAmount;

    @Column(name = "discount_amount")
    private java.math.BigDecimal discountAmount;

    @Column(name = "total_amount")
    private java.math.BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_number")
    @JsonBackReference
    private Order order;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "modification_time")
    private LocalDateTime modificationTime;

    @Column(name = "notes")
    private String notes;

    public BigDecimal calculatedTotalAmount(){
     return BigDecimal.valueOf(this.getNetAmount().doubleValue() + this.getTaxAmount().doubleValue() - this.getDiscountAmount().doubleValue());
    }
}