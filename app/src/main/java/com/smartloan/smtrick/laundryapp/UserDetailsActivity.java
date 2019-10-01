package com.smartloan.smtrick.laundryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UserDetailsActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private TextView name, email, mobile, city, area, store;
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        name = (TextView) findViewById(R.id.user_name);
        store = (TextView) findViewById(R.id.user_store1);
        email = (TextView) findViewById(R.id.user_email1);
        mobile = (TextView) findViewById(R.id.user_mobile1);
//        city = (TextView) findViewById(R.id.user_city1);
//        area = (TextView) findViewById(R.id.user_area1);

        user = (Users) getIntent().getSerializableExtra("report");

        name.setText(user.getName());
//        store.setText(user.getStorename());
        email.setText(user.getEmail());
//        mobile.setText(user.getContact());
//        city.setText(user.getCity());
//        area.setText(user.getArea());
    }
}
