package com.cameco.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class User {
    private Long id;
    private String login;
    private String password;
    private Integer role;
    private Date dateReg;
    private Date dateActivation;
    private String prefix;
    private String e_mail;
    private String name;
    private String fullName;
    private Long package_id;

}
