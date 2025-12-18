package com.college;

import java.util.List;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private List<User> followers;

    public User () {
    }

    public User(String firstName, String lastName, String phone, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
