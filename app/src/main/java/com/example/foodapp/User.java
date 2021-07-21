package com.example.foodapp;

import com.google.firebase.auth.FirebaseUser;

public class User  {
    public String username,email,phone,password,isStaff;

    public User(){
    }

    public User(String username,String email ,String phone,String password) {
        this.email= email;
        this.phone= phone;
        this.username= username;
        this.password=password;


    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
