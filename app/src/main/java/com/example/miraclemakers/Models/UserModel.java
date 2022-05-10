package com.example.miraclemakers.Models;

import java.io.Serializable;

public class UserModel implements Serializable {

    private int user_id;
    private String name;
    private String email;
    private String password;
    private int category_id;

    public UserModel( int user_id, String name, String email, String password, int category_id) {

        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.category_id = category_id;
    }
    public UserModel()
    {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
