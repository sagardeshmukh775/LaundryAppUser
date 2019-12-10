package com.washtag.smtrick.user_laundryapp.Models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Types {


    ArrayList<String> kg;
    ArrayList<String> piece;

    public String serviceId;


    public Types() {
    }

    public Types(String serviceId) {

        this.serviceId = serviceId;
        this.kg = new ArrayList<String>();
        this.kg = new ArrayList<String>();

    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ArrayList<String> getKg() {
        return kg;
    }

    public void setKg(ArrayList<String> kg) {
        this.kg = kg;
    }

    public ArrayList<String> getPiece() {
        return piece;
    }

    public void setPiece(ArrayList<String> piece) {
        this.piece = piece;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("serviceId",getServiceId() );
        leedMap.put("kg",getKg() );
        leedMap.put("piece",getPiece() );

        return leedMap;

    }
}
