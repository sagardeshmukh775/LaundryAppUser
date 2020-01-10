package com.cleanandcomfort.smtrick.user_laundryapp.Constants;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;

public class Constant {
    /************************************** Firebase Storage reference constants ***************************************************************************/
    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    public static final DatabaseReference USER_TABLE_REF = DATABASE.getReference("users");
    public static final DatabaseReference INVOICE_TABLE_REF = DATABASE.getReference("invoice");
    public static final DatabaseReference SERVICES_TABLE_REF = DATABASE.getReference("services");
    public static final DatabaseReference SUBCATEGORY_TABLE_REF = DATABASE.getReference("SubCategory");
    public static final DatabaseReference USER_SERVICES_TABLE_REF = DATABASE.getReference("UserServices");
    public static final DatabaseReference REQUESTS_TABLE_REF = DATABASE.getReference("Requests");
    public static final DatabaseReference SERVICE_PROVIDERS_TABLE_REF = DATABASE.getReference("ServiceProviders");
    public static final DatabaseReference SERVICE_PROVIDERS_SERVICES_TABLE_REF = DATABASE.getReference("ServiceProvidersservices");
    public static final DatabaseReference WASH_TABLE_REF = DATABASE.getReference("Wash");
    public static final DatabaseReference TIMESLOT_TABLE_REF = DATABASE.getReference("TimeSlot");
    public static final DatabaseReference TYPES_TABLE_REF = DATABASE.getReference("Types");

    /************************************** Firebase Authentication reference constants ***************************************************************************/
    public static final FirebaseAuth AUTH = FirebaseAuth.getInstance();
    /************************************** Calender Constatns ***************************************************************************/
    public static final Calendar cal = Calendar.getInstance();
    public static final int DAY = cal.get(Calendar.DAY_OF_MONTH);
    public static final int MONTH = cal.get(Calendar.MONTH);
    public static final int YEAR = cal.get(Calendar.YEAR);
    public static final String EMAIL_POSTFIX = "@cleanandcomfort.com";
    //********************************************STATUS FLEADS*****************************
    public static final String STATUS_GENERATED = "GENERATED";
    public static final String STATUS_APPROVED = "APPROVED";

    public static final String ROLE_SERVICE_PROVIDER = "SERVICE PROVIDER";
    public static final String ROLE_USER = "USER";
    public static final String USER_STATUS_ACTIVE = "ACTIVE";

    //****************************************************************
    public static final String LEED_MODEL = "LEED_MODEL";

    public static final FirebaseStorage STORAGE = FirebaseStorage.getInstance();



}
