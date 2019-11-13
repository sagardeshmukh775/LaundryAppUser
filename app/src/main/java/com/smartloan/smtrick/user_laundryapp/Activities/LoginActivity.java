package com.smartloan.smtrick.user_laundryapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.user_laundryapp.Preferences.AppSharedPreference;
import com.smartloan.smtrick.user_laundryapp.Exception.ExceptionUtil;
import com.smartloan.smtrick.user_laundryapp.Dialogue.ProgressDialogClass;
import com.smartloan.smtrick.user_laundryapp.R;
import com.smartloan.smtrick.user_laundryapp.Repository.UserRepository;
import com.smartloan.smtrick.user_laundryapp.Models.User;
import com.smartloan.smtrick.user_laundryapp.Repository.Impl.UserRepositoryImpl;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private EditText inputMobile, inputPassword;
    private Button btnSignup, btnLogin, btnReset;
    private ProgressDialogClass progressDialog;
    private AppSharedPreference appSharedPreference;
    private DatabaseReference mDatabase;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity_User.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appSharedPreference = new AppSharedPreference(this);
        userRepository = new UserRepositoryImpl(this);

        checkLoginState();

        inputMobile = (EditText) findViewById(R.id.number);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        if (isNetworkAvailable()) {
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private void Login() {
        progressDialog = new ProgressDialogClass(this);
        progressDialog.showDialog(this.getString(R.string.SIGNING_IN), this.getString(R.string.PLEASE_WAIT));
        String code = "+91";
        String number = inputMobile.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        if (number.isEmpty() || number.length() < 10) {
            inputMobile.setError("Valid number is required");
            inputMobile.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();
            return;
        }

        final String phoneNumber = number;


        DatabaseReference Dref = FirebaseDatabase.getInstance().getReference("users");
        Dref.orderByChild("number").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        User upload = postSnapshot.getValue(User.class);

                        progressDialog.dismissDialog();
                        String userid = upload.getUserid();

                        if (inputPassword.getText().toString().equalsIgnoreCase(upload.getPassword())) {
                            appSharedPreference.addUserDetails(upload);
                            appSharedPreference.createUserLoginSession();
                            Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            if (upload.getRole().equalsIgnoreCase("USER")) {
                                logintoapp();
                            }else if (upload.getRole().equalsIgnoreCase("SERVICE PROVIDER")){
                                logintoapp();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();

                        }

                    }


                } else {
                    progressDialog.dismissDialog();
                    Toast.makeText(LoginActivity.this, "Login failed Please Register", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void signInUserData(final String userId) {
//        userRepository.readUserByUserId(userId, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                if (object != null) {
//                    User user = (User) object;
//                    appSharedPreference.createUserLoginSession();
//                    appSharedPreference.addUserDetails(user);
//
//                    logintoapp();
//
//                } else {
//                    Utility.showTimedSnackBar(LoginActivity.this, inputPassword, getMessage(R.string.login_fail_try_again));
//                }
//                if (progressDialog != null)
//                    progressDialog.dismissDialog();
//            }
//
//            @Override
//            public void onError(Object object) {
//                if (progressDialog != null)
//                    progressDialog.dismissDialog();
//                Utility.showTimedSnackBar(LoginActivity.this, inputPassword, getMessage(R.string.login_fail_try_again));
//            }
//        });
//    }

    private void logintoapp() {
        Intent i = new Intent(this, MainActivity_User.class);
        startActivity(i);
        finish();
    }


    public void checkLoginState() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (appSharedPreference != null && appSharedPreference.getUserLoginStatus()) {
                        if (appSharedPreference.getGeneratedId() != null && appSharedPreference.getUserid() != null) {

                            String roll = appSharedPreference.getRole();

                            if (roll.equalsIgnoreCase("USER")) {
                                logintoapp();
                            }else if (roll.equalsIgnoreCase("SERVICE PROVIDER")){
                                logintoapp();
                            }
//                            logintoapp();
//                            loginTotellecallerApp();
//                            if (roll.equals("User")) {
//                                logintoapp();
//
//                            } else if (roll.equals("Service Provider")) {
//                                logintoapp();
//
//                            }
                        }
                    }
                } catch (Exception e) {
                    ExceptionUtil.logException(e);
                }
            }
        });
    }

    private String getMessage(int id) {
        return getString(id);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

