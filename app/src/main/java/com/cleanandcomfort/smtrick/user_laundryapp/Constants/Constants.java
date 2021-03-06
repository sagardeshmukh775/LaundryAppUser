package com.cleanandcomfort.smtrick.user_laundryapp.Constants;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by Belal on 2/23/2017.
 */

public class Constants {

    public static final String STORAGE_PATH_UPLOADS = "NewImage/";
    public static final String DATABASE_PATH_UPLOADS = "NewImage";
    public static final String ADVERTISE_PATH_UPLOADS = "Advertise";
    public static final String DATABASE_PATH_PATIENTS = "Patients";
    public static final String CHANNEL_ID = "samar app";

    public static final String CALANDER_DATE_FORMATE = "dd/MM/yy";

    public static final Calendar cal = Calendar.getInstance();
    public static final int DAY = cal.get(Calendar.DAY_OF_MONTH);
    public static final int MONTH = cal.get(Calendar.MONTH);
    public static final int YEAR = cal.get(Calendar.YEAR);

    public static String TWO_DIGIT_LIMIT = "%02d";
    public static String FOUR_DIGIT_LIMIT = "%04d";

    public static final String SUCCESS = "Success";
    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    public static final DatabaseReference LEEDS_TABLE_REF = DATABASE.getReference("leeds");

    public static final DatabaseReference USER_TABLE_REF = DATABASE.getReference("users");

    public static final String AGENT_PREFIX = "USER- ";
    public static final String RELATION_PREFIX = "REL- ";
    public static final DatabaseReference MEMBERS_TABLE_REF = DATABASE.getReference("Members");
    public static final DatabaseReference RELATIVES_TABLE_REF = DATABASE.getReference("Relations");
    public static final DatabaseReference USERS_TABLE_REF = DATABASE.getReference("users");

    public static final String FCM_PUSH_URL = "https://fcm.googleapis.com/fcm/send";


}
