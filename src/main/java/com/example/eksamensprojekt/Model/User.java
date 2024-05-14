package com.example.eksamensprojekt.Model;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String role;
    private int user_ID;
    private List<User> userList;

    public User() {

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public void setUserList(List<User> userList) {
        userList = userList;
    }

    public List<User> getuserList() {
        return userList;
    }
}
