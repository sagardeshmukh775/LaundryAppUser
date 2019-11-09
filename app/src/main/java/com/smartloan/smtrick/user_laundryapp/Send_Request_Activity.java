package com.smartloan.smtrick.user_laundryapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.Timer;
import java.util.TimerTask;

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
    static private List<String> serList;
    private List<Upload> adds;
    private List<Upload> adds1;

    private String subitem;
    AppSharedPreference appSharedPreference;
    String userId;
    User user;

    LeedRepository leedRepository;
    private DatePickerDialog mDatePickerDialog;
    String fdate;
    int mHour;
    int mMinute;

    Spinner spinnerwash, spinnerTime,  spinnerWeights;
    EditText edtVenders;
    RelativeLayout layoutRandomTime;
    LinearLayout sliderDotspanel;
    ViewPager viewPager;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;
    private ImageView[] dots;
    private static int NUM_PAGES = 0;

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

        uploads = new ArrayList<>();
        serList = new ArrayList<>();
        adds = new ArrayList<>();
        adds1 = new ArrayList<>();

        appSharedPreference = new AppSharedPreference(Send_Request_Activity.this);
        leedRepository = new LeedRepositoryImpl();
        user = (User) getIntent().getSerializableExtra(Constant.LEED_MODEL);
        userId = user.getUserid();

        String[] washType = new String[]{
                "Wash and Fold",
                "Wash and Iron", "Iron", "Dry Clean"};

        String[] TimeSlot = new String[]{
                "24 Hours",
                "12 Hours", "48 Hours", "Random"};

        String[] Weights = new String[]{
                "Kg Wise",
                "piece Wise"};


        SendRequest = (Button) findViewById(R.id.request);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinnerwash = (Spinner) findViewById(R.id.spinnerwashtype);
        spinnerTime = (Spinner) findViewById(R.id.spinnertimeslot);
        edtVenders = (EditText) findViewById(R.id.edtvenders);
        spinnerWeights = (Spinner) findViewById(R.id.spinnerweights);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.sppinner_layout_listitem, washType);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerwash.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                this, R.layout.sppinner_layout_listitem, TimeSlot);
        spinnerArrayAdapter3.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerTime.setAdapter(spinnerArrayAdapter3);

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, R.layout.sppinner_layout_listitem, Weights);
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerWeights.setAdapter(spinnerArrayAdapter2);

        layoutRandomTime = (RelativeLayout) findViewById(R.id.layoutrandomtime);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        progressDialog = new ProgressDialog(this);

        getSupportActionBar().setTitle(subitem);  // provide compatibility to all the versions

        Query queryadds = FirebaseDatabase.getInstance().getReference("Advertise");

        queryadds.addValueEventListener(valueEventListener1);

        viewPager = findViewById(R.id.viewPager);
        dots = new ImageView[0];

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        Query query = FirebaseDatabase.getInstance().getReference("UserServices").orderByChild("userId").equalTo(userId);

        query.addValueEventListener(valueEventListener);

        SendRequest.setOnClickListener(this);

        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerTime.getSelectedItem().toString().equalsIgnoreCase("Random")) {
                    showotherRelation();
                } else {
                    hideotherRelation();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    public void onImageClick(String imageData, boolean isChecked) {
        if (isChecked) {
            serList.add(imageData);
        } else if (!isChecked) {
            int i = serList.indexOf(imageData);
            serList.remove(i);
        }
    }

    public void showotherRelation() {
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) layoutRandomTime.getLayoutParams();
        params1.height = -1;
        layoutRandomTime.setLayoutParams(params1);
    }

    public void hideotherRelation() {
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) layoutRandomTime.getLayoutParams();
        params1.height = 0;
        layoutRandomTime.setLayoutParams(params1);
    }

    ValueEventListener valueEventListener1 = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            adds.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Upload upload = postSnapshot.getValue(Upload.class);

                adds.add(upload);
                int size = adds.size() - 1;
                adds1.clear();
                for (int i = size; i >= 0; i--) {
                    adds1.add(adds.get(i));
                }

            }
            NUM_PAGES = adds1.size();
//            showDots();
            AddsAdapter adapter = new AddsAdapter(getApplicationContext(), adds1);
            viewPager.setAdapter(adapter);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), 500, 4000);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            Send_Request_Activity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < NUM_PAGES - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


//    public void showDots() {
//
//        dots = new ImageView[uploads.size()];
//
//        // dots = new ImageView[dotscount];
//
//        for (int i = 0; i < uploads.size(); i++) {
//
//            dots[i] = new ImageView(getApplicationContext());
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            sliderDotspanel.addView(dots[i], params);
//
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                try {
//
//                    for (int i = 0; i < uploads.size(); i++) {
//                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
//                    }
//
//                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//                } catch (Exception e) {
//
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }

}
