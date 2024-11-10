package com.assignment.freshly.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    private int c_id;

    private String email;

    private String password;

    private String imagePath;

    private String Gender;

    public Customer() {
    }

    public Customer(String email, String password, String imagePath, String gender) {
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
        this.Gender = gender;
    }

    public Customer(String email, String password, String gender){
        this.email = email;
        this.password = password;
        this.Gender = gender;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
}

