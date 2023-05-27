package com.example.appmongodb.Adapters;

public class User {
    private int id;
    private String address, email, name, password, phone;
    private String passwordText;

    public User(){

    }

    public User(int id, String address, String passwordText, String email, String name, String password, String phone) {
        this.id = id;
        this.address = address;
        this.email = email;
        this.name = name;
        this.password = password;
        this.passwordText = passwordText;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return passwordText;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
