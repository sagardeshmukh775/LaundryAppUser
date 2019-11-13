package com.smartloan.smtrick.user_laundryapp.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wash {


    ArrayList<String> washandfold;
    ArrayList<String> washandiron;
    ArrayList<String> iron;
    ArrayList<String> dryclean;
    public String serviceId;


    public Wash() {
    }

    public Wash(String serviceId) {

        this.serviceId = serviceId;
        this.washandfold = new ArrayList<String>();
        this.washandiron = new ArrayList<String>();
        this.iron = new ArrayList<String>();
        this.dryclean = new ArrayList<String>();
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ArrayList<String> getWashandfold() {
        return washandfold;
    }

    public void setWashandfold(ArrayList<String> washandfold) {
        this.washandfold = washandfold;
    }

    public ArrayList<String> getWashandiron() {
        return washandiron;
    }

    public void setWashandiron(ArrayList<String> washandiron) {
        this.washandiron = washandiron;
    }

    public ArrayList<String> getIron() {
        return iron;
    }

    public void setIron(ArrayList<String> iron) {
        this.iron = iron;
    }

    public ArrayList<String> getDryclean() {
        return dryclean;
    }

    public void setDryclean(ArrayList<String> dryclean) {
        this.dryclean = dryclean;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("serviceId",getServiceId() );
        leedMap.put("washandfold",getWashandfold() );
        leedMap.put("washandiron",getWashandiron() );
        leedMap.put("iron",getIron() );
        leedMap.put("dryclean",getDryclean() );

        return leedMap;

    }
}
