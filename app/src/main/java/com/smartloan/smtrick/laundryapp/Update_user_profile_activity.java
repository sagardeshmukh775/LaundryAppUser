package com.smartloan.smtrick.laundryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Update_user_profile_activity extends AppCompatActivity {

    EditText inputUsername,inputMobile,inputAddress,inputPinCode,inputPassword;
    Spinner spinnerRole;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);


    }
}
