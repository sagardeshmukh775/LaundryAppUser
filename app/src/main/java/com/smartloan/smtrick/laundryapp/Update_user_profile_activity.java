package com.smartloan.smtrick.laundryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class Update_user_profile_activity extends AppCompatActivity {

    EditText inputUsername, inputMobile, inputAddress, inputPinCode, inputPassword,spinnerRole;
    Button btnUpdate;
    private AppSharedPreference appSharedPreference;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appSharedPreference = new AppSharedPreference(this);

        inputUsername = (EditText) findViewById(R.id.username);
        inputMobile = (EditText) findViewById(R.id.mobilenumber);
        inputAddress = (EditText) findViewById(R.id.address);
        inputPinCode = (EditText) findViewById(R.id.pincode);
        inputPassword = (EditText) findViewById(R.id.password);
        spinnerRole = (EditText) findViewById(R.id.spinnerselectusertype);
        btnUpdate = (Button) findViewById(R.id.update_button);

        getUserDetails();

    }

    private void getUserDetails() {
        inputUsername.setText(appSharedPreference.getName());
        inputMobile.setText(appSharedPreference.getNumber());
        inputAddress.setText(appSharedPreference.getAddress());
        inputPinCode.setText(appSharedPreference.getPincode());
        inputPassword.setText(appSharedPreference.getPassword());
        spinnerRole.setText(appSharedPreference.getRole());
    }
}
