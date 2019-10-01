package com.smartloan.smtrick.laundryapp;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    public String name, number, address, pincode, password,role, userid,generatedId;

    public User() {

    }

    User(String name, String number, String userid,String address, String pincode, String password,String generatedId,
         String role) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.pincode = pincode;
        this.password = password;
        this.userid = userid;
        this.generatedId = generatedId;
        this.role = role;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGeneratedId() {
        return generatedId;
    }

    public void setGeneratedId(String generatedId) {
        this.generatedId = generatedId;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("name", getName());
        leedMap.put("number", getNumber());
        leedMap.put("address", getAddress());
        leedMap.put("pincode", getPincode());
        leedMap.put("password", getPassword());
        leedMap.put("role", getRole());
        leedMap.put("userid", getUserid());
        leedMap.put("generatedId", getGeneratedId());

        return leedMap;

    }
}
