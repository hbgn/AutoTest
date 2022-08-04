package com.apit.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;

    public String toString(){
        return (
            "{id:"+ id +","+
            "username:"+ username +","+
            "age:"+ age +","+
            "sex:"+ sex +","+
            "permission:"+ permission +","+
            "isDelete:"+ isDelete +"}"
        );
    }
}
