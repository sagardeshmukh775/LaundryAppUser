package com.cleanandcomfort.smtrick.user_laundryapp.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ServiceProviderServices {

    public String washandfold;
    public String washandiron;
    public String iron;
    public String dryclean;
    public String twodays;
    public String oneday;
    public String halfday;
    public String random;
    public String kg;
    public String piece;

    public String serviceproviderName;
    public String serviceId;


    public ServiceProviderServices() {
    }

    public ServiceProviderServices(String washandfold, String washandiron, String iron,
                                   String dryclean, String twodays, String oneday,
                                   String halfday, String random, String kg,
                                   String piece, String serviceproviderName, String serviceId) {

        this.washandfold = washandfold;
        this.washandiron = washandiron;
        this.iron = iron;
        this.dryclean = dryclean;
        this.twodays = twodays;
        this.oneday = oneday;
        this.halfday = halfday;
        this.random = random;
        this.kg = kg;
        this.piece = piece;
        this.serviceproviderName = serviceproviderName;
        this.serviceId = serviceId;



    }

    public String getWashandfold() {
        return washandfold;
    }

    public void setWashandfold(String washandfold) {
        this.washandfold = washandfold;
    }

    public String getWashandiron() {
        return washandiron;
    }

    public void setWashandiron(String washandiron) {
        this.washandiron = washandiron;
    }

    public String getIron() {
        return iron;
    }

    public void setIron(String iron) {
        this.iron = iron;
    }

    public String getDryclean() {
        return dryclean;
    }

    public void setDryclean(String dryclean) {
        this.dryclean = dryclean;
    }

    public String getTwodays() {
        return twodays;
    }

    public void setTwodays(String twodays) {
        this.twodays = twodays;
    }

    public String getOneday() {
        return oneday;
    }

    public void setOneday(String oneday) {
        this.oneday = oneday;
    }

    public String getHalfday() {
        return halfday;
    }

    public void setHalfday(String halfday) {
        this.halfday = halfday;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getServiceproviderName() {
        return serviceproviderName;
    }

    public void setServiceproviderName(String serviceproviderName) {
        this.serviceproviderName = serviceproviderName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("washandfold", getWashandfold());
        leedMap.put("washandiron", getWashandiron());
        leedMap.put("iron", getIron());
        leedMap.put("dryclean", getDryclean());
        leedMap.put("twodays", getTwodays());
        leedMap.put("oneday", getOneday());
        leedMap.put("halfday", getHalfday());
        leedMap.put("random", getRandom());
        leedMap.put("kg", getKg());
        leedMap.put("piece", getPiece());
        leedMap.put("serviceproviderName", getServiceproviderName());
        leedMap.put("serviceId", getServiceId());

        return leedMap;
    }
}
