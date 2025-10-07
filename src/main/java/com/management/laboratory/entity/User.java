package com.management.laboratory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String account;
    private String password;
    private int permission;
    private String id;
}
