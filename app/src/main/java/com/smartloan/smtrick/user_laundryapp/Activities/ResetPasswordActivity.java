package com.smartloan.smtrick.user_laundryapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.smartloan.smtrick.user_laundryapp.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputNumber,inputName;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputNumber = (EditText) findViewById(R.id.number);
        inputName = (EditText) findViewById(R.id.name);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = inputNumber.getText().toString().trim();
                String name = inputNumber.getText().toString().trim();

                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(getApplication(), "Enter your registered Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplication(), "Enter your registered Name", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

}
