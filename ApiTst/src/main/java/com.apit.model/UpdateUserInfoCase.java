package com.apit.model;

import lombok.Data;

@Data
public class UpdateUserInfoCase {
    private int id;
    private int userId;
    private String username;
    private String sex;
    private String age;
    private String permission;
    private String isDelete;
    private String expected;
}
