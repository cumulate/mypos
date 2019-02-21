package com.tmt.pos.mypos.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tmt.pos.mypos.persistence.util.StringPrefixedSequenceIdGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.Order")
@Table(name = "Orders")
@EqualsAndHashCode(of = {"orderNumber"})
//@ToString(exclude = {"orderItemList"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order")
    @GenericGenerator(
            name = "order",
            strategy = "com.tmt.pos.mypos.persistence.util.StringPrefixedSequenceIdGenerator",
            //strategy = "sequence",
            parameters = {
                    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "order_seq"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ORD_"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d") })
    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "net_amount")
    private java.math.BigDecimal netAmount;

    @Column(name = "tax_amount")
    private java.math.BigDecimal taxAmount;

    @Column(name = "discount_amount")
    private java.math.BigDecimal discountAmount;

    @Column(name = "total_amount")
    private java.math.BigDecimal totalAmount;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "payment_status")
    private String paymentStatus;

    @OneToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "modification_time")
    private LocalDateTime modificationTime;

    @Column(name = "notes")
    private String notes;


    public void addOrderItem(OrderItem orderItem){
        this.orderItemList.add(orderItem);
        orderItem.setOrder(this);
        orderItem.setCreationTime(this.creationTime);
        orderItem.setModificationTime(this.modificationTime);
        orderItem.setCreatedBy(this.createdBy);
    }

}