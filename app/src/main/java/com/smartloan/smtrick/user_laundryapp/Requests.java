package com.smartloan.smtrick.user_laundryapp;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Requests {

    public String serviceProviderId;
    public String userId;
    public String userPinCode;
    public String userMobile;
    public String userAddress;
    public String requestId;
    public String status;
    public String date;
    public String userName;
    List<String> serviceList;


    public Requests() {
    }

    public Requests(String serviceProviderId, String userId, String userPinCode, String userMobile
            , String userAddress, String requestId,String date,String status,String userName) {
        this.serviceProviderId = serviceProviderId;
        this.userId = userId;
        this.userPinCode = userPinCode;
        this.userMobile = userMobile;
        this.userAddress = userAddress;
        this.requestId = requestId;
        this.date = date;
        this.status = status;
        this.userName = userName;
        this.serviceList = new ArrayList<String>();
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPinCode() {
        return userPinCode;
    }

    public void setUserPinCode(String userPinCode) {
        this.userPinCode = userPinCode;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap();

        leedMap.put("serviceProviderId", getServiceProviderId());
        leedMap.put("userId", getUserId());
        leedMap.put("userPinCode", getUserPinCode());
        leedMap.put("userMobile",getUserMobile() );
        leedMap.put("userAddress",getUserAddress() );
        leedMap.put("requestId",getRequestId() );
        leedMap.put("date",getDate() );
        leedMap.put("status",getStatus() );
        leedMap.put("userName",getUserName() );
        leedMap.put("serviceList",getServiceList() );

        return leedMap;

    }
}
