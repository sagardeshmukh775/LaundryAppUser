package com.smartloan.smtrick.user_laundryapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import static com.itextpdf.text.error_messages.MessageLocalization.getMessage;
import static com.smartloan.smtrick.user_laundryapp.Constants.AGENT_PREFIX;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputMobile, inputPassword;
    private EditText inputUsername, inputAddress, inputPincode, inputOTP;
    Spinner spinnerRole;

    private Button btnSignIn, btnSignUp, btnResetPassword, btnOTP;
    private ProgressBar progressBar;
    private ProgressDialogClass progressDialogClass;
    private AppSharedPreference appSharedPreference;
    private FirebaseAuth auth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        progressDialogClass = new ProgressDialogClass(this);
        appSharedPreference = new AppSharedPreference(this);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        btnOTP = (Button) findViewById(R.id.button_generate_otp);

        inputUsername = (EditText) findViewById(R.id.username);
        inputMobile = (EditText) findViewById(R.id.mobilenumber);
        inputAddress = (EditText) findViewById(R.id.address);
        inputPincode = (EditText) findViewById(R.id.pincode);
        inputPassword = (EditText) findViewById(R.id.password);
        inputOTP = (EditText) findViewById(R.id.otp);


        String[] Userstypeall = new String[]{
                "USER",
                "SERVICE PROVIDER"};
        spinnerRole = (Spinner) findViewById(R.id.spinnerselectusertype);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.sppinner_layout_listitem, Userstypeall
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerRole.setAdapter(spinnerArrayAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);


        if (isNetworkAvailable()) {

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        btnResetPassword.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnOTP.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignUp) {

            String code = inputOTP.getText().toString();
//            validateAndCreateUser();
            verifyCode(code);

        } else if (v == btnSignIn) {

        } else if (v == btnResetPassword) {

        } else if (v == btnOTP) {
            progressBar.setVisibility(View.VISIBLE);
            String phonenumber ="+91" + inputMobile.getText().toString()  ;
            sendVerificationCode(phonenumber);

        }
    }


    private void validateAndCreateUser() {
        User user = fillUserModel();
        if (validate(user))
            createUser(user);
    }

    private boolean validate(User user) {
        String validationMessage;
        boolean isValid = true;

        try {
            if (Utility.isEmptyOrNull(user.getName())) {
                validationMessage = "User Name Should not be empty";
                inputUsername.setError(validationMessage);
                isValid = false;
            }
            if (Utility.isEmptyOrNull(user.getNumber())) {
                validationMessage = getString(R.string.MOBILE_NUMBER_SHOULD_NOT_BE_EMPTY);
                inputMobile.setError(validationMessage);
                isValid = false;
            }else if (!Utility.isValidMobileNumber(user.getNumber())) {
                validationMessage = getMessage(String.valueOf(R.string.INVALID_MOBILE_NUMBER));
                inputMobile.setError(validationMessage);
                isValid = false;
            }
            if (Utility.isEmptyOrNull(user.getPassword())) {
                validationMessage = getString(R.string.PASSWORD_SHOULD_NOT_BE_EMPTY);
                inputPassword.setError(validationMessage);
                isValid = false;
            }
        } catch (Exception e) {
            isValid = false;
            ExceptionUtil.logException( e);
        }

        return isValid;
    }

    private User fillUserModel() {
        User user = new User();
        user.setName(inputUsername.getText().toString());
        user.setNumber(inputMobile.getText().toString());
        user.setAddress(inputAddress.getText().toString());
        user.setPincode(inputPincode.getText().toString());
        user.setPassword(inputPassword.getText().toString());
        user.setRole(spinnerRole.getSelectedItem().toString());
        user.setUserid(Utility.generateAgentId(AGENT_PREFIX));
        user.setGeneratedId(auth.getInstance().getCurrentUser().getUid());


        return user;
    }

    private void createUser(final User user) {

        String userid = user.getGeneratedId();

        FirebaseDatabase.getInstance().getReference("users")
                .child(userid)
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(SignupActivity.this, "Registered Successfully",
                            Toast.LENGTH_LONG).show();
                    addUserDataToPreferences(user);
                loginToApp();
                progressBar.setVisibility(View.INVISIBLE);

                } else {

                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //        userRepository.createUser(user, new CallBack() {
//            @Override
//            public void onSuccess(Object object) {
//                addUserDataToPreferences(user);
//                loginToApp();
//            }
//
//            @Override
//            public void onError(Object object) {
//                Utility.showMessage(SignupActivity.this, getMessage(""));
//                progressDialogClass.dismissDialog();
//            }
//        });
    }

    private void addUserDataToPreferences(User user) {
        appSharedPreference.addUserDetails(user);
        appSharedPreference.createUserLoginSession();
    }

    private void loginToApp() {
        Toast.makeText(SignupActivity.this, "User Register Successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void sendVerificationCode(String number) {
//        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                inputOTP.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            validateAndCreateUser();

                        } else {
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    //                final String email = inputEmail.getText().toString().trim();
//                String password = inputPassword.getText().toString().trim();
//                final String username = Username.getText().toString().trim();
////                final String storename = Storename.getText().toString().trim();
////                final String status = "REQUEST";
//
////                final String contact = userContact.getText().toString().trim();
//
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (password.length() < 6) {
//                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                progressBar.setVisibility(View.VISIBLE);
//                //create user
//                auth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);
//                                if (task.isSuccessful()) {
//                                    auth.getCurrentUser().sendEmailVerification()
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//                                                        String userid = auth.getInstance().getCurrentUser().getUid();
//                                                        User user = new User();
//                                                        user.setName(username);
//                                                        user.setEmail(email);
//                                                        user.setUserid(userid);
//                                                        user.setLanguage("English");
//                                                        FirebaseDatabase.getInstance().getReference("users")
//                                                                .child(userid)
//                                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                if (task.isSuccessful()) {
//
//                                                                    Toast.makeText(SignupActivity.this, "Registered Successfully, Please check your Email for verification",
//                                                                            Toast.LENGTH_LONG).show();
//
//                                                                } else {
//
//                                                                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
//                                                    } else {
//
//                                                        Toast.makeText(SignupActivity.this, task.getException().getMessage(),
//                                                                Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//                                }
//
//                                // If sign in fails, display a message to the user. If sign in succeeds
//                                // the auth state listener will be notified and logic to handle the
//                                // signed in user can be handled in the listener.
//                                if (!task.isSuccessful()) {
//                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
}