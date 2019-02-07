package com.techclub.mckvie;

public class User {
    public String name, email, id, admin, dept;

    public User(){

    }

    public User(String name, String email, String id, String admin, String dept) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.admin = admin;
        this.dept = dept;
    }
}