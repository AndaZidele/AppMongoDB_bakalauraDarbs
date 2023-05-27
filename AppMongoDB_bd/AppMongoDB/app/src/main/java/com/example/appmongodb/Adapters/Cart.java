package com.example.appmongodb.Adapters;


public class Cart {
    private int prod_id, user_id, amount;
    private String prod_name;
    private float price;

    public Cart() {
    }

    public Cart(int prod_id, int user_id, String prod_name, Float price, int amount) {
        this.prod_id = prod_id;
        this.user_id = user_id;
        this.price = price;
        this.prod_name = prod_name;
        this.amount = amount;
    }



    public int getId() {
        return prod_id;
    }

    public void setId(int id) {
        this.prod_id = prod_id;
    }

    public int getUsers_id() {
        return user_id;
    }

    public void setUsers_id(int users_id) {
        this.user_id = users_id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return prod_name;
    }

    public void setName(String name) {
        this.prod_name = prod_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
