package com.tmt.pos.mypos.entities;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "com.tmt.pos.mypos.entities.Users")
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy    = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}