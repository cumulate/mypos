package com.tmt.pos.mypos.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.Payment")
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "payment_amount")
    private java.math.BigDecimal paymentAmount;

    @OneToMany(mappedBy = "payment", fetch = FetchType.EAGER)
    private List<PaymentDetails> paymentDetailsList = new ArrayList<>();

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_time")
    private Date creationTime;

    @Column(name = "modification_time")
    private Date modificationTime;

    public void addPayment(PaymentDetails paymentDetails){
        this.paymentDetailsList.add(paymentDetails);
        paymentDetails.setPayment(this);
    }
}