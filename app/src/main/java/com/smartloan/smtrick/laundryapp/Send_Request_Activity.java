package com.smartloan.smtrick.laundryapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Send_Request_Activity extends AppCompatActivity implements View.OnClickListener, OnImageClickListener {
    //recyclerview object
    private RecyclerView recyclerView;
    Button SendRequest;
    EditText edtDateTime;

    //adapter object
    private RecyclerView.Adapter adapter;
    //database reference
    private DatabaseReference mDatabase;
    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<UserServices> uploads;
    private List<String> serList;

    private String subitem, mainitem;
    AppSharedPreference appSharedPreference;
    String userId;
    User user;

    LeedRepository leedRepository;
    private DatePickerDialog mDatePickerDialog;
    String fdate;
    int mHour;
    int mMinute;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appSharedPreference = new AppSharedPreference(Send_Request_Activity.this);
        leedRepository = new LeedRepositoryImpl();
        user = (User) getIntent().getSerializableExtra(Constant.LEED_MODEL);
        userId = user.getUserid();

        SendRequest = (Button) findViewById(R.id.request);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        progressDialog = new ProgressDialog(this);

        getSupportActionBar().setTitle(subitem);  // provide compatibility to all the versions

        uploads = new ArrayList<>();
        serList = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        Query query = FirebaseDatabase.getInstance().getReference("UserServices").orderByChild("userId").equalTo(userId);

        query.addValueEventListener(valueEventListener);

        SendRequest.setOnClickListener(this);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            progressDialog.dismiss();
            //iterating through all the values in database
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                UserServices upload = postSnapshot.getValue(UserServices.class);

                uploads.add(upload);
            }
            //creating adapter
            adapter = new Request_Adapter(Send_Request_Activity.this, uploads);
            //adding adapter to recyclerview
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();

        }
    };

    @Override
    public void onClick(View v) {
        if (v == SendRequest) {
            final Dialog dialog1 = new Dialog(Send_Request_Activity.this);
            dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialogboxanimation);
            dialog1.setContentView(R.layout.dialog_select_date);

            edtDateTime = (EditText) dialog1.findViewById(R.id.txtdatetime);
            Button Add = (Button) dialog1.findViewById(R.id.btnsendrequest);
            Button cancle = (Button) dialog1.findViewById(R.id.btncancle);

            setDateTimeField();
            edtDateTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatePickerDialog.show();

                }
            });

            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Requests requests = new Requests();
                    requests.setServiceProviderId(user.getUserid());
                    requests.setUserId(appSharedPreference.getUserid());
                    requests.setUserName(appSharedPreference.getName());
                    requests.setUserAddress(appSharedPreference.getAddress());
                    requests.setUserMobile(appSharedPreference.getNumber());
                    requests.setUserPinCode(appSharedPreference.getPincode());
                    requests.setDate(edtDateTime.getText().toString());
                    requests.setServiceList(serList);
                    requests.setStatus(Constant.STATUS_GENERATED);
                    requests.setRequestId(Constant.REQUESTS_TABLE_REF.push().getKey());
                    leedRepository.sendRequest(requests, new CallBack() {
                        @Override
                        public void onSuccess(Object object) {
                            Toast.makeText(Send_Request_Activity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });
                }
            });
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });

            dialog1.show();
        }
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(Send_Request_Activity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                fdate = sd.format(startDate);

                timePicker();
            }

            private void timePicker() {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Send_Request_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;

                                edtDateTime.setText(fdate + " " + hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onImageClick(List<String> imageData) {
        serList = imageData;
    }
}
