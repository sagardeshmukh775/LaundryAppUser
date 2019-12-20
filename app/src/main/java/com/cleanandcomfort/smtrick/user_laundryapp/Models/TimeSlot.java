package com.cleanandcomfort.smtrick.user_laundryapp.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeSlot {


    ArrayList<String> twodays;
    ArrayList<String> oneday;
    ArrayList<String> halfday;
    ArrayList<String> random;
    public String serviceId;


    public TimeSlot() {
    }

    public TimeSlot(String serviceId) {

        this.serviceId = serviceId;
        this.twodays = new ArrayList<String>();
        this.oneday = new ArrayList<String>();
        this.halfday = new ArrayList<String>();
        this.random = new ArrayList<String>();
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ArrayList<String> getTwodays() {
        return twodays;
    }

    public void setTwodays(ArrayList<String> twodays) {
        this.twodays = twodays;
    }

    public ArrayList<String> getOneday() {
        return oneday;
    }

    public void setOneday(ArrayList<String> oneday) {
        this.oneday = oneday;
    }

    public ArrayList<String> getHalfday() {
        return halfday;
    }

    public void setHalfday(ArrayList<String> halfday) {
        this.halfday = halfday;
    }

    public ArrayList<String> getRandom() {
        return random;
    }

    public void setRandom(ArrayList<String> random) {
        this.random = random;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("serviceId",getServiceId() );
        leedMap.put("twodays",getTwodays() );
        leedMap.put("oneday",getOneday() );
        leedMap.put("halfday",getHalfday() );
        leedMap.put("random",getRandom() );

        return leedMap;

    }
}
