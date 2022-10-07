package com.example.askyoursenior.model;

public class User {
    String name;
    String username;
    String phone_number;
    String organisationname;


    public User() {
    }

    public User(String name, String username, String phone_number, String uid, String organisationname) {
        this.name = name;
        this.username = username;
        this.phone_number = phone_number;
        this.organisationname = organisationname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getOrganisationname() {
        return organisationname;
    }

    public void setOrganisationname(String organisationname) {
        this.organisationname = organisationname;
    }
}
