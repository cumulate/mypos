package com.tmt.pos.mypos.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.Customer")
@Table(name = "Customer")
@EqualsAndHashCode(of = {"customerCode"})
public class Customer implements BaseEntity<String> {


    @Id
    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;


    @Column(name = "zipcode")
    private String zipcode;

    @OneToOne()
    @JoinColumn(name = "sales_man")
    private User salesMan;


    @Override
    public String getId() {
        return customerCode;
    }
}