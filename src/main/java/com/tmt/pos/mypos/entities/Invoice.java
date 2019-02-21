package com.tmt.pos.mypos.entities;

import com.tmt.pos.mypos.persistence.util.StringPrefixedSequenceIdGenerator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.Invoice")
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq")
    @GenericGenerator(
            name = "invoice_seq",
            strategy = "com.tmt.pos.mypos.persistence.util.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "invoice_seq"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ORD_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d") })
    @Column(name = "invoice_number")
    private String invoiceNumber;

    /**
     * @Column(name = "order_number")
     * private String orderNumber;
     */
    @OneToMany(fetch = FetchType.LAZY )
    @JoinColumn(name = "order_number")
    private List<Order> orderList;


    @Column(name = "invoice_amount")
    private java.math.BigDecimal invoiceAmount;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_time")
    private Date creationTime;

    @Column(name = "modification_time")
    private Date modificationTime;
}