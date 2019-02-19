package com.tmt.pos.mypos.entities;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.Customer")
@Table(name = "Customer")
public class Customer {


    @Id
    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "zipcode")
    private String zipcode;

    @OneToOne
    @JoinColumn(name = "sales_man" )
    private Users salesMan;
}