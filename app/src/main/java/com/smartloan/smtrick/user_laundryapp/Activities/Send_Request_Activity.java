package com.smartloan.smtrick.user_laundryapp.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.user_laundryapp.Adapters.AddsAdapter;
import com.smartloan.smtrick.user_laundryapp.Adapters.Providers_Adapter;
import com.smartloan.smtrick.user_laundryapp.Adapters.RateCardAdapter;
import com.smartloan.smtrick.user_laundryapp.Adapters.Request_Adapter;
import com.smartloan.smtrick.user_laundryapp.CallBack.CallBack;
import com.smartloan.smtrick.user_laundryapp.Constants.Constant;
import com.smartloan.smtrick.user_laundryapp.Constants.Constants;
import com.smartloan.smtrick.user_laundryapp.Listeners.OnImageClickListener;
import com.smartloan.smtrick.user_laundryapp.Listeners.OnRecycleClickListener;
import com.smartloan.smtrick.user_laundryapp.Models.Requests;
import com.smartloan.smtrick.user_laundryapp.Models.ServiceProviderServices;
import com.smartloan.smtrick.user_laundryapp.Models.TimeSlot;
import com.smartloan.smtrick.user_laundryapp.Models.Types;
import com.smartloan.smtrick.user_laundryapp.Models.Upload;
import com.smartloan.smtrick.user_laundryapp.Models.User;
import com.smartloan.smtrick.user_laundryapp.Models.UserServices;
import com.smartloan.smtrick.user_laundryapp.Models.Wash;
import com.smartloan.smtrick.user_laundryapp.Preferences.AppSharedPreference;
import com.smartloan.smtrick.user_laundryapp.R;
import com.smartloan.smtrick.user_laundryapp.Repository.Impl.LeedRepositoryImpl;
import com.smartloan.smtrick.user_laundryapp.Repository.Impl.UserRepositoryImpl;
import com.smartloan.smtrick.user_laundryapp.Repository.LeedRepository;
import com.smartloan.smtrick.user_laundryapp.Repository.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Send_Request_Activity extends AppCompatActivity implements View.OnClickListener, OnImageClickListener, OnRecycleClickListener {
    //recyclerview object
    private RecyclerView recyclerView;
    Button SendRequest;
    EditText edtDateTime;

    //adapter object
    private RecyclerView.Adapter adapter;
    Providers_Adapter adapter_new;
    //database reference
    private DatabaseReference mDatabase;
    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<UserServices> uploads;
    private List<Uri> RateCardImagesList;
    private List<String> RateCardImagesList1;
    private RateCardAdapter uploadListAdapter;
    static private List<String> serList;
    private List<Upload> adds;
    private List<Upload> adds1;
    private ArrayList<User> userList;

    private String subitem;
    AppSharedPreference appSharedPreference;
    String userId;
    User user0;
    User user1;

    LeedRepository leedRepository;
    private DatePickerDialog mDatePickerDialog;
    String fdate;
    int mHour;
    int mMinute;

    Spinner spinnerwash, spinnerTime, spinnerWeights;
    EditText edtVenders, edtRandomTime;
    RelativeLayout layoutRandomTime;
    LinearLayout sliderDotspanel;
    ViewPager viewPager;
    private ImageView[] dots;
    private static int NUM_PAGES = 0;
    UserRepository userRepository;
    ArrayList<User> UserArraylist;
    List<String> ServiceProviders;
    ArrayList<ServiceProviderServices> ServicesList;

    ArrayList<Wash> washList;
    ArrayList<String> wash;
    Wash wash2;
    ArrayList<TimeSlot> TimeList;
    ArrayList<String> Time;
    TimeSlot timeSlot;
    ArrayList<Types> TypeList;
    ArrayList<String> type;
    Types types;

    ArrayList<String> commonList;
    ArrayList<String> commonList3;

    ImageView imspplus, imspminus, imlhpluse, imlhminus,
            imblpluse, imblminus,
            imspluse, imsminus,
            imjhpluse, imjhminus,
            imbedpluse, imbedminus,
            imbkpluse, imbkminus;
    EditText edspcount, edlhcount, edblcount, edscount, edjhcount, edbedcount, edbkcount;
    CheckBox chsp, chlh, chbl, chs, chjh, chbed, chbk;
    int i = 0, j = 0, k = 0, l = 0, m = 0, n = 0, o = 0;
    CardView pieceCard;

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;

    private int revealX;
    private int revealY;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);


        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.activity_show_images);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Place Order");

        uploads = new ArrayList<>();
        RateCardImagesList = new ArrayList<>();
        RateCardImagesList1 = new ArrayList<>();
        serList = new ArrayList<>();
        adds = new ArrayList<>();
        adds1 = new ArrayList<>();
        UserArraylist = new ArrayList<>();
        ServiceProviders = new ArrayList<>();
        ServicesList = new ArrayList<>();
        userList = new ArrayList<>();

        washList = new ArrayList<>();
        TimeList = new ArrayList<>();
        TypeList = new ArrayList<>();

        wash = new ArrayList<>();
        Time = new ArrayList<>();
        type = new ArrayList<>();
        commonList = new ArrayList<>();
        commonList3 = new ArrayList<>();

        appSharedPreference = new AppSharedPreference(Send_Request_Activity.this);
        progressDialog = new ProgressDialog(this);
        leedRepository = new LeedRepositoryImpl();
        userRepository = new UserRepositoryImpl();

        String[] washType = new String[]{"Select Wash Types",
                "Wash and Fold",
                "Wash and Iron", "Iron", "Dry Clean"};

        String[] TimeSlot = new String[]{"Select Time Slot",
                "24 Hours",
                "12 Hours", "48 Hours", "Random"};

        String[] Weights = new String[]{"Select Types",
                "Kg Wise",
                "piece Wise"};

        SendRequest = (Button) findViewById(R.id.request);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewratecard);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        layoutRandomTime = (RelativeLayout) findViewById(R.id.layoutrandomtime);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        viewPager = findViewById(R.id.viewPager);

        spinnerwash = (Spinner) findViewById(R.id.spinnerwashtype);
        spinnerTime = (Spinner) findViewById(R.id.spinnertimeslot);
        edtVenders = (EditText) findViewById(R.id.edtvenders);
        edtRandomTime = (EditText) findViewById(R.id.txtotherrelationship1);
        spinnerWeights = (Spinner) findViewById(R.id.spinnerweights);


        pieceCard = (CardView) findViewById(R.id.piecescard);
        pieceCard.setVisibility(View.GONE);

        chsp = (CheckBox) findViewById(R.id.checkshirtandpant);
        chlh = (CheckBox) findViewById(R.id.checkLehanga);
        chbl = (CheckBox) findViewById(R.id.checkBlazer);
        chs = (CheckBox) findViewById(R.id.checkSaree);
        chjh = (CheckBox) findViewById(R.id.checkJH);
        chbed = (CheckBox) findViewById(R.id.checkBedsheet);
        chbk = (CheckBox) findViewById(R.id.checkBlanket);

        edspcount = (EditText) findViewById(R.id.spcount);
        edlhcount = (EditText) findViewById(R.id.lhcount);
        edblcount = (EditText) findViewById(R.id.blcount);
        edscount = (EditText) findViewById(R.id.scount);
        edjhcount = (EditText) findViewById(R.id.jhcount);
        edbedcount = (EditText) findViewById(R.id.bedcount);
        edbkcount = (EditText) findViewById(R.id.bkcount);

        imspplus = (ImageView) findViewById(R.id.spplus);
        imspminus = (ImageView) findViewById(R.id.spminus);
        imlhpluse = (ImageView) findViewById(R.id.lhplus);
        imlhminus = (ImageView) findViewById(R.id.lhminus);
        imblpluse = (ImageView) findViewById(R.id.blplus);
        imblminus = (ImageView) findViewById(R.id.blminus);
        imspluse = (ImageView) findViewById(R.id.splus);
        imsminus = (ImageView) findViewById(R.id.sminus);
        imjhpluse = (ImageView) findViewById(R.id.jhplus);
        imjhminus = (ImageView) findViewById(R.id.jhminus);
        imbedpluse = (ImageView) findViewById(R.id.bedplus);
        imbedminus = (ImageView) findViewById(R.id.bedminus);
        imbkpluse = (ImageView) findViewById(R.id.bkplus);
        imbkminus = (ImageView) findViewById(R.id.bkminus);

        imspplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i + 1;
                edspcount.setText(String.valueOf(i));
            }
        });
        imspminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(edspcount.getText().toString());
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "Please increase", Toast.LENGTH_SHORT).show();
                } else {
                    i = i - 1;
                    edspcount.setText(String.valueOf(i));
                }
            }
        });
        imlhpluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                j = j + 1;
                edlhcount.setText(String.valueOf(j));
            }
        });
        imlhminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(edlhcount.getText().toString());
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "Please increase", Toast.LENGTH_SHORT).show();
                } else {
                    i = i - 1;
                    edlhcount.setText(String.valueOf(i));
                }
            }
        });
        imblpluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                k = k + 1;
                edblcount.setText(String.valueOf(k));
            }
        });
        imblminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(edblcount.getText().toString());
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "Please increase", Toast.LENGTH_SHORT).show();
                } else {
                    i = i - 1;
                    edblcount.setText(String.valueOf(i));
                }
            }
        });
        imspluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l = l + 1;
                edscount.setText(String.valueOf(l));
            }
        });
        imsminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(edscount.getText().toString());
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "Please increase", Toast.LENGTH_SHORT).show();
                } else {
                    i--;
                    edscount.setText(String.valueOf(i));
                }
            }
        });
        imjhpluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m = m + 1;
                edjhcount.setText(String.valueOf(m));
            }
        });
        imjhminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(edjhcount.getText().toString());
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "Please increase", Toast.LENGTH_SHORT).show();
                } else {
                    i--;
                    edjhcount.setText(String.valueOf(i));
                }
            }
        });
        imbedpluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                n = n + 1;
                edbedcount.setText(String.valueOf(n));
            }
        });
        imbedminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(edbedcount.getText().toString());
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "Please increase", Toast.LENGTH_SHORT).show();
                } else {
                    i--;
                    edbedcount.setText(String.valueOf(i));
                }
            }
        });
        imbkpluse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                o = o + 1;
                edbkcount.setText(String.valueOf(o));
            }
        });
        imbkminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(edbkcount.getText().toString());
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "Please increase", Toast.LENGTH_SHORT).show();
                } else {
                    i--;
                    edbkcount.setText(String.valueOf(i));
                }
            }
        });

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

        Query queryadds = FirebaseDatabase.getInstance().getReference("Advertise");
        queryadds.addValueEventListener(valueEventListener1);

        dots = new ImageView[0];

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        SendRequest.setOnClickListener(this);

        spinnerwash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String washItem = spinnerwash.getSelectedItem().toString();
                userRepository.readWash(new CallBack() {
                    @Override
                    public void onSuccess(Object object) {
                        washList = (ArrayList<Wash>) object;
                        for (int i = 0; i < washList.size(); i++) {
                            wash2 = washList.get(i);

                        }
                        try {
                            wash.clear();
                            if (washItem.equalsIgnoreCase("Wash and Fold")) {
                                wash.addAll(wash2.getWashandfold());

                            } else if (washItem.equalsIgnoreCase("Wash and Iron")) {
                                wash.addAll(wash2.getWashandiron());

                            } else if (washItem.equalsIgnoreCase("Iron")) {
                                wash.addAll(wash2.getIron());

                            } else if (washItem.equalsIgnoreCase("Dry Clean")) {
                                wash.addAll(wash2.getDryclean());

                            }

                        } catch (Exception e) {
                            Toast.makeText(Send_Request_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String time = spinnerTime.getSelectedItem().toString();
                userRepository.readTimeSlot(new CallBack() {
                    @Override
                    public void onSuccess(Object object) {
                        TimeList = (ArrayList<TimeSlot>) object;
                        for (int i = 0; i < TimeList.size(); i++) {
                            timeSlot = TimeList.get(i);

                        }
                        try {
                            Time.clear();
                            if (time.equalsIgnoreCase("24 Hours")) {
                                Time.addAll(timeSlot.getOneday());
                                hideotherRelation();

                            } else if (time.equalsIgnoreCase("12 Hours")) {
                                Time.addAll(timeSlot.getHalfday());
                                hideotherRelation();

                            } else if (time.equalsIgnoreCase("48 Hours")) {
                                Time.addAll(timeSlot.getTwodays());
                                hideotherRelation();

                            } else if (time.equalsIgnoreCase("Random")) {
                                Time.addAll(timeSlot.getRandom());
                                showotherRelation();
                            } else {
                                hideotherRelation();
                            }

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerWeights.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String weight = spinnerWeights.getSelectedItem().toString();
                userRepository.readTypes(new CallBack() {
                    @Override
                    public void onSuccess(Object object) {
                        TypeList = (ArrayList<Types>) object;
                        for (int i = 0; i < TypeList.size(); i++) {
                            types = TypeList.get(i);

                        }
                        try {
                            type.clear();
                            if (weight.equalsIgnoreCase("Kg Wise")) {
                                type.addAll(types.getKg());
                                pieceCard.setVisibility(View.GONE);

                            } else if (weight.equalsIgnoreCase("piece Wise")) {
                                type.addAll(types.getPiece());
                                pieceCard.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Toast.makeText(Send_Request_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtVenders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(Send_Request_Activity.this);
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialogboxanimation);
                dialog1.setContentView(R.layout.dialog_service_providers);

                RecyclerView Providers_recycle = (RecyclerView) dialog1.findViewById(R.id.recycler_view_service_provicers);
                Providers_recycle.setHasFixedSize(true);
                Providers_recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                String wash5 = spinnerwash.getSelectedItem().toString();
                String time5 = spinnerTime.getSelectedItem().toString();
                String weight5 = spinnerWeights.getSelectedItem().toString();
                if (!wash5.equalsIgnoreCase("Select Wash Types") && time5.equalsIgnoreCase("Select Time Slot") && weight5.equalsIgnoreCase("Select Types")) {
                    ReadServiseProviders(wash, Providers_recycle, dialog1);

                } else if (wash5.equalsIgnoreCase("Select Wash Types") && !time5.equalsIgnoreCase("Select Time Slot") && weight5.equalsIgnoreCase("Select Types")) {
                    ReadServiseProviders(Time, Providers_recycle, dialog1);

                } else if (wash5.equalsIgnoreCase("Select Wash Types") && time5.equalsIgnoreCase("Select Time Slot") && !weight5.equalsIgnoreCase("Select Types")) {
                    ReadServiseProviders(type, Providers_recycle, dialog1);

                } else if (!wash5.equalsIgnoreCase("Select Wash Types") && !time5.equalsIgnoreCase("Select Time Slot") && weight5.equalsIgnoreCase("Select Types")) {

                    commonList.clear();
                    for (int i = 0; i < wash.size(); i++) {
                        for (int j = 0; j < Time.size(); j++) {
                            if (wash.get(i).equalsIgnoreCase(Time.get(j))) {
                                commonList.add(wash.get(i));
//                                Toast.makeText(Send_Request_Activity.this, commonList.get(i), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    ReadServiseProviders(commonList, Providers_recycle, dialog1);

                } else if (!wash5.equalsIgnoreCase("Select Wash Types") && time5.equalsIgnoreCase("Select Time Slot") && !weight5.equalsIgnoreCase("Select Types")) {
                    commonList3.clear();
                    for (int i = 0; i < wash.size(); i++) {
                        for (int j = 0; j < type.size(); j++) {
                            if (wash.get(i).equalsIgnoreCase(type.get(j))) {
                                commonList3.add(wash.get(i));
//                                Toast.makeText(Send_Request_Activity.this, commonList3.get(i), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    ReadServiseProviders(commonList3, Providers_recycle, dialog1);

                } else if (wash5.equalsIgnoreCase("Select Wash Types") && !time5.equalsIgnoreCase("Select Time Slot") && !weight5.equalsIgnoreCase("Select Types")) {
                    commonList3.clear();
                    for (int i = 0; i < Time.size(); i++) {
                        for (int j = 0; j < type.size(); j++) {
                            if (Time.get(i).equalsIgnoreCase(type.get(j))) {
                                commonList3.add(Time.get(i));
//                                Toast.makeText(Send_Request_Activity.this, commonList3.get(i), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    ReadServiseProviders(commonList3, Providers_recycle, dialog1);

                } else if (!wash5.equalsIgnoreCase("Select Wash Types") && !time5.equalsIgnoreCase("Select Time Slot") && !weight5.equalsIgnoreCase("Select Types")) {

                    commonList.clear();
                    commonList3.clear();
                    for (int i = 0; i < wash.size(); i++) {
                        for (int j = 0; j < Time.size(); j++) {
                            if (wash.get(i).equalsIgnoreCase(Time.get(j))) {
                                commonList.add(wash.get(i));
//                                Toast.makeText(Send_Request_Activity.this, commonList.get(i), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    for (int i = 0; i < commonList.size(); i++) {
                        for (int j = 0; j < type.size(); j++) {
                            if (commonList.get(i).equalsIgnoreCase(type.get(j))) {
                                commonList3.add(commonList.get(i));
//                                Toast.makeText(Send_Request_Activity.this, commonList3.get(i), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    ReadServiseProviders(commonList3, Providers_recycle, dialog1);
                }

                dialog1.show();
                Window window = dialog1.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

        });
    }

    private void ReadServiseProviders(ArrayList<String> commonList, RecyclerView recyclerView, Dialog dialog1) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        userList.clear();
        for (int i = 0; i < commonList.size(); i++) {
            String id = commonList.get(i);
            userRepository.readServiceProviderById(id, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        user1 = (User) object;
                        userList.add(user1);
                    }

                    adapter_new = new Providers_Adapter(getApplicationContext(), userList, (OnRecycleClickListener) Send_Request_Activity.this, dialog1);
                    //adding adapter to recyclerview
                    recyclerView.setAdapter(adapter_new);
                    progressDialog.dismiss();
                }

                @Override
                public void onError(Object object) {
                    progressDialog.dismiss();
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (v == SendRequest) {
            final Dialog dialog1 = new Dialog(Send_Request_Activity.this);
            dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialogboxanimation);
            dialog1.setContentView(R.layout.dialog_select_date);

            edtDateTime = (EditText) dialog1.findViewById(R.id.txtdatetime);
            Button Add = (Button) dialog1.findViewById(R.id.btnsendrequest);
            Button cancle = (Button) dialog1.findViewById(R.id.btncancle);

            String name = edtVenders.getText().toString();
            userRepository.readServiceProviderByName(name, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    user0 = (User) object;
                }

                @Override
                public void onError(Object object) {

                }
            });

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

                    if (chsp.isChecked()) {
                        serList.add(chsp.getText().toString() + " - " + edspcount.getText().toString());
                    }
                    if (chlh.isChecked()) {
                        serList.add(chlh.getText().toString() + " - " + edlhcount.getText().toString());
                    }
                    if (chbl.isChecked()) {
                        serList.add(chbl.getText().toString() + " - " + edblcount.getText().toString());
                    }
                    if (chs.isChecked()) {
                        serList.add(chs.getText().toString() + " - " + edscount.getText().toString());
                    }
                    if (chjh.isChecked()) {
                        serList.add(chjh.getText().toString() + " - " + edjhcount.getText().toString());
                    }
                    if (chbed.isChecked()) {
                        serList.add(chbed.getText().toString() + " - " + edbedcount.getText().toString());
                    }
                    if (chbk.isChecked()) {
                        serList.add(chbk.getText().toString() + " - " + edbkcount.getText().toString());
                    }

                    Requests requests = new Requests();
                    requests.setServiceProviderId(user0.getUserid());
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

                            sendFCMPush(user0.getTokan());
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

    @Override
    public void onRecycleClick(User user) {
        //user0 = user;
        edtVenders.setText(user.getName());
        try {
            RateCardImagesList1 = user.getImageList();
            if (RateCardImagesList1 != null) {
                recyclerView.setVisibility(View.VISIBLE);
                for (String image : RateCardImagesList1) {
                    RateCardImagesList.add(Uri.parse(image));
                }
                uploadListAdapter = new RateCardAdapter(Send_Request_Activity.this, RateCardImagesList);
                recyclerView.setLayoutManager(new LinearLayoutManager(Send_Request_Activity.this, LinearLayoutManager.HORIZONTAL, true));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(uploadListAdapter);
            }else {
               recyclerView.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }
        uploads.clear();
        Query query = FirebaseDatabase.getInstance().getReference("UserServices").orderByChild("userId").equalTo(user.getUserid());

        query.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            uploads.clear();
            progressDialog.dismiss();
            //iterating through all the values in database
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                UserServices upload = postSnapshot.getValue(UserServices.class);

                uploads.add(upload);
            }
            //creating adapter
            adapter = new Request_Adapter(Send_Request_Activity.this, uploads);
            //adding adapter to recyclerview
//            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();

        }

    };


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


    private void sendFCMPush(String Token) {

        String Legacy_SERVER_KEY = "AIzaSyCM5Eb6ZrYBWhzGRSsm5WKYlzlT7BlhuKs";
        String msg = "New Order From" + appSharedPreference.getName();
        String title = "New Order Has Been Received";
        String token = Token;

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();

            try {
                objData.put("body", msg);
                objData.put("title", title);
                objData.put("sound", "default");
                objData.put("icon", "icon_name"); //   icon_name image must be there in drawable
                objData.put("tag", token);
                objData.put("priority", "high");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            obj.put("to", token);
            //obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("!_@rj@_@@_PASS:>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.FCM_PUSH_URL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("!_@@_SUCESS", response + "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("!_@@_Errors--", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Legacy_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }


    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }
}
