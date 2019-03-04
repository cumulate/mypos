package com.tmt.pos.mypos.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.PaymentDetails")
@Table(name = "PaymentDetails")
@EqualsAndHashCode(of = {"paymentDetailsId"})
public class PaymentDetails implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_details_id")
    private Long paymentDetailsId;

   /* @Column(name = "invoice_number")
    private String invoiceNumber;*/

    @OneToOne
    @JoinColumn(name = "invoice_number")
    private Invoice invoice;

   /* @Column(name = "invoice_amount")
    private java.math.BigDecimal invoiceAmount;*/

  /*  @Column(name = "payment_status")
    private String paymentStatus;*/

    /*@Column(name = "payment_id")
    private Long paymentId;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;


    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_time")
    private Date creationTime;

    @Column(name = "modification_time")
    private Date modificationTime;

    @Override
    public Long getId() {
        return paymentDetailsId;
    }
}