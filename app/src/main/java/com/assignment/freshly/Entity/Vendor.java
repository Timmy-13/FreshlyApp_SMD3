package com.assignment.freshly.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vendor")
public class Vendor {

    @PrimaryKey(autoGenerate = true)
    private int v_id;

    private String username;

    private String password;

    private byte[] imagePath;

    private String phone;

    private String address;

    public Vendor(int vendorId, String username, String password, String phone, String address) {
        this.username = username;
        this.v_id = vendorId;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public Vendor(String username, String password,String phone, String address) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImagePath() {
        return imagePath;
    }

    public void setImagePath(byte[] imagePath) {
        this.imagePath = imagePath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }
}
