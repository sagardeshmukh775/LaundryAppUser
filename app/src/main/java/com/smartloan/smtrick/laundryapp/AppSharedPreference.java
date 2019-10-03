package com.smartloan.smtrick.laundryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class AppSharedPreference {
    private SharedPreferences sharedPref;
    private Context context;
    private SharedPreferences.Editor editor;
    private String USERNAME = "USERNAME";
    private String PASSWORD = "PASSWORD";
    private String EMAIL_ID = "EMAIL_ID";
    private String MOBILE_NO = "MOBILE_NO";
    private String AADHAAR_NO = "AADHAAR_NO";
    private String PROFILE_SMALL_IMAGE = "PROFILE_SMALL_IMAGE";
    private String PROFILE_LARGE_IMAGE = "PROFILE_LARGE_IMAGE";
    private String REG_ID = "REG_ID";
    private String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    private String USER_ID = "USER_ID";
    private String CREATED_DATE_TIME = "CREATED_DATE_TIME";
    private String UPDATED_DATE_TIME = "UPDATED_DATE_TIME";
    private String REGISTRATION_TOKEN = "REGISTRATION_TOKEN";
    private String ADDRESS = "ADDRESS";
    private String PINCODE = "PINCODE";
    private String ROLE = "ROLE";
    private String GENDER = "GENDER";
    private String AGENT_ID = "AGENT_ID";


    public AppSharedPreference(Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void addUserDetails(User user) {
        editor = sharedPref.edit();
        if (user != null) {
            if (user.getName() != null)
                editor.putString(USERNAME, (user.getName()));
            if (user.getNumber() != null)
                editor.putString(MOBILE_NO, (user.getNumber()));
            if (user.getAddress() != null)
                editor.putString(ADDRESS, (user.getAddress()));
            if (user.getPincode() != null)
                editor.putString(PINCODE, (user.getPincode()));
            if (user.getPassword() != null)
                editor.putString(PASSWORD, (user.getPassword()));
            if (user.getUserid() != null)
                editor.putString(USER_ID, (user.getUserid()));
            if (user.getGeneratedId() != null)
                editor.putString(REGISTRATION_TOKEN, (user.getGeneratedId()));
//            if (user.getUserId() != null)
//                editor.putString(USER_ID, (user.getUserId()));
//            if (user.getRegistrationToken() != null)
//                editor.putString(REGISTRATION_TOKEN, (user.getRegistrationToken()));
//            if (user.getAddress() != null)
//                editor.putString(ADDRESS, (user.getAddress()));
            if (user.getRole() != null)
                editor.putString(ROLE, (user.getRole()));
//            if (user.getGender() != null)
//                editor.putString(GENDER, (user.getGender()));
//            if (user.getAgentId() != null)
//                editor.putString(AGENT_ID, (user.getAgentId()));
        }
        editor.apply();
    }

    public void createUserLoginSession() {
        editor = sharedPref.edit();
        editor.putBoolean(IS_USER_LOGGED_IN, true);
        editor.apply();
    }

    //User
    public String getName() {
        return (sharedPref.getString(USERNAME, ""));
    }

    public String getPassword() {
        return (sharedPref.getString(PASSWORD, ""));
    }

    public String getNumber() {
        return (sharedPref.getString(MOBILE_NO, ""));
    }

    public String getPincode() {
        return (sharedPref.getString(PINCODE, ""));
    }

    public String getAddress() {
        return (sharedPref.getString(ADDRESS, ""));
    }

    public String getRole() {
        return (sharedPref.getString(ROLE, ""));
    }


    public String getUserid() { return (sharedPref.getString(USER_ID, "2iXUwm71jKbi0yy3594PTOTHgbR2")); }

    public String getGeneratedId() {
        return (sharedPref.getString(REGISTRATION_TOKEN, ""));
    }

    public String getCreatedDateTime() {
        return (sharedPref.getString(CREATED_DATE_TIME, ""));
    }

    public String getUpdatedDateTime() {
        return (sharedPref.getString(UPDATED_DATE_TIME, ""));
    }


    public boolean getUserLoginStatus() {
        return sharedPref.getBoolean(IS_USER_LOGGED_IN, (false));
    }

//    public String getRole() {
//        return (sharedPref.getString(ROLE, ""));
//    }

//    public String getGender() {
//        return (sharedPref.getString(GENDER, ""));
//    }

//    public String getRegId() {
//        return (sharedPref.getString(REG_ID, ""));
//    }

//    public String getEmaiId() {
//        return (sharedPref.getString(EMAIL_ID, ""));
//    }

//    public String getAgeniId() {
//        return (sharedPref.getString(AGENT_ID, ""));
//    }
//
//    public String getMobileNo() {
//        return (sharedPref.getString(MOBILE_NO, ""));
//    }

//    public String getAadhaarNo() {
//        return (sharedPref.getString(AADHAAR_NO, ""));
//    }

//    public String getProfileSmallImage() {
//        return (sharedPref.getString(PROFILE_SMALL_IMAGE, ""));
//    }

//    public String getProfileLargeImage() {
//        return (sharedPref.getString(PROFILE_LARGE_IMAGE, ""));
//    }

    public void setUserProfileImages(String imagePath) {
        editor = sharedPref.edit();
        if (imagePath != null) {
            editor.putString(PROFILE_SMALL_IMAGE, imagePath);
            editor.putString(PROFILE_LARGE_IMAGE, imagePath);
        }
        editor.apply();
    }




    public void clear() {
        editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }
}
