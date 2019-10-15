package com.smartloan.smtrick.laundryapp;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserServices {

    public String maincat;
    public String userId;
    public String serviceId;
    ArrayList<String> sublist;


    public UserServices() {
    }

    public UserServices(String maincat,String userId,String serviceId) {
        this.maincat = maincat;
        this.userId = userId;
        this.serviceId = serviceId;
        this.sublist = new ArrayList<String>();
    }

    public String getMaincat() {
        return maincat;
    }

    public void setMaincat(String maincat) {
        this.maincat = maincat;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ArrayList<String> getSublist() {
        return sublist;
    }

    public void setSublist(ArrayList<String> sublist) {
        this.sublist = sublist;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("maincat", getMaincat());
        leedMap.put("userId", getUserId());
        leedMap.put("serviceId", getServiceId());
        leedMap.put("sublist",getSublist() );

        return leedMap;

    }
}
