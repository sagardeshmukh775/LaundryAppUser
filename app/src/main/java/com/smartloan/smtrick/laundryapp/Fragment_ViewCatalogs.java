package com.smartloan.smtrick.laundryapp;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Fragment_ViewCatalogs extends Fragment {

    private RecyclerView catalogRecycler;
    private DatabaseReference mdataRefpatient;
    private ArrayList<MemberVO> catalogList;
    private ProgressBar catalogprogress;
    private CatalogAdapter adapter;
    private EditText edtSearch;
    DatabaseReference databaseReference;
    String Language;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser Fuser;
    private String uid;

    // int[] animationList = {R.anim.layout_animation_up_to_down};
    int i = 0;

    ImageButton Whatsapp, Message;
    String number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcatalog, container, false);

        // getActivity().getActionBar().setTitle("Products");
        catalogprogress = (ProgressBar) view.findViewById(R.id.catalog_progress);
        catalogRecycler = (RecyclerView) view.findViewById(R.id.catalog_recycle);
        edtSearch = (EditText) view.findViewById(R.id.search_edit_text);
        Whatsapp = (ImageButton) view.findViewById(R.id.whatsapp);
        Message = (ImageButton) view.findViewById(R.id.message);
        catalogList = new ArrayList<>();

        getCurrentuserdetails();

        if (isNetworkAvailable()) {
//            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                     * Clear the list when editText is empty
                     * */
                    catalogList.clear();
                    catalogRecycler.removeAllViews();
                }

            }
        });
        Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(getContext());
                dialog1.setContentView(R.layout.customdialogbox4);

                Button dialogEditButton = (Button) dialog1.findViewById(R.id.dialogButtonOK);
                Button dialogEditButtoncancle = (Button) dialog1.findViewById(R.id.dialogButtonCancle);
                EditText message = (EditText) dialog1.findViewById(R.id.whatsappmessage1);


                dialogEditButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int j = 0; j < catalogList.size(); j++) {
                            number = catalogList.get(j).getMembercontact();
                            if (number != null) {
                                PackageManager packageManager = getActivity().getPackageManager();
                                Intent i = new Intent(Intent.ACTION_VIEW);

                                try {
                                    String message1 = message.getText().toString();
                                    String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(message1, "UTF-8");
                                    i.setPackage("com.whatsapp");
                                    i.setData(Uri.parse(url));
                                    if (i.resolveActivity(packageManager) != null) {
                                        getActivity().startActivity(i);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
//                        Toast.makeText(getContext(), "Sent", Toast.LENGTH_SHORT).show();
                    }
                });

                dialogEditButtoncancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });

                dialog1.show();

            }


        });

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog1 = new Dialog(getContext());
                dialog1.setContentView(R.layout.customdialogbox4);

                Button dialogEditButton = (Button) dialog1.findViewById(R.id.dialogButtonOK);
                Button dialogEditButtoncancle = (Button) dialog1.findViewById(R.id.dialogButtonCancle);
                EditText message = (EditText) dialog1.findViewById(R.id.whatsappmessage1);

                dialogEditButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            for (int j = 0; j < catalogList.size(); j++) {
                                number = catalogList.get(j).getMembercontact();
                                if (number != null) {

                                    sendMySMS();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }

                    private void sendMySMS() {

                        String message1 = message.getText().toString();

                        //Check if the phoneNumber is empty
                        if (number.isEmpty() || message1.isEmpty()) {
                            Toast.makeText(getContext(), "Please Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
                        } else {

                            SmsManager sms = SmsManager.getDefault();
                            // if message length is too long messages are divided
                            List<String> messages = sms.divideMessage(message1);
                            for (String msg : messages) {

                                PendingIntent sentIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent("SMS_SENT"), 0);
                                PendingIntent deliveredIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent("SMS_DELIVERED"), 0);
                                sms.sendTextMessage(number, null, msg, sentIntent, deliveredIntent);
                                dialog1.dismiss();
                            }
                            Toast.makeText(getContext(), "Message Sent", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                dialogEditButtoncancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        });

        return view;
    }


    private void setAdapter(final String toString) {

        databaseReference.child("Members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                catalogList.clear();
                catalogRecycler.removeAllViews();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    MemberVO leedsModel = snapshot.getValue(MemberVO.class);

                    if (leedsModel.getMemberbirthdate() != null && leedsModel.getMembercast() != null
                            && leedsModel.getMembereducation() != null
                            && leedsModel.getMemberage() != null) {
                        if (leedsModel.getMemberbirthdate().toLowerCase().contains(toString)) {

                            catalogList.add(leedsModel);
                        } else if (leedsModel.getMembercast().toLowerCase().contains(toString)) {
                            catalogList.add(leedsModel);

                        } else if (leedsModel.getMembereducation().toLowerCase().contains(toString)) {
                            catalogList.add(leedsModel);

                        } else if (leedsModel.getMemberage().toLowerCase().contains(toString)) {
                            catalogList.add(leedsModel);

                        }
                    }

                }

                serAdapter(catalogList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void serAdapter(ArrayList<MemberVO> leedsModels) {
        if (leedsModels != null) {
            if (adapter == null) {
                adapter = new CatalogAdapter(getActivity(), leedsModels, Language);
                catalogRecycler.setAdapter(adapter);
                //   onClickListner();
            } else {
                ArrayList<MemberVO> leedsModelArrayList = new ArrayList<>();
                leedsModelArrayList.addAll(leedsModels);
                adapter.reload(leedsModelArrayList);
            }
        }
    }


    private void getCurrentuserdetails() {

        try {
            firebaseAuth = FirebaseAuth.getInstance();

            Fuser = firebaseAuth.getCurrentUser();
            uid = Fuser.getUid();
            uid = Fuser.getDisplayName();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot usersnapshot : dataSnapshot.getChildren()) {

//                        Language = usersnapshot.child("language").getValue(String.class);
//                        if (Language.equalsIgnoreCase("Marathi")) {
//                            setLanguage();
//                        } else if (Language.equalsIgnoreCase("English")) {
//                            edtSearch.setHint("Search Text...");
//                            setLeeds();
//                        }
                    }
                }

                private void setLanguage() {
                    edtSearch.setHint(R.string.edt_search);

                    setLeeds();
                }

                private void setLeeds() {

                    catalogprogress.setVisibility(View.VISIBLE);
                    mdataRefpatient = FirebaseDatabase.getInstance().getReference("Members");
                    mdataRefpatient.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            catalogList.clear();
                            for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                                MemberVO mainProducts = mainproductSnapshot.getValue(MemberVO.class);
                                catalogList.add(mainProducts);
                            }
                            try {
                                adapter = new CatalogAdapter(getActivity(), catalogList, Language);
                                //adding adapter to recyclerview
                                catalogRecycler.setAdapter(adapter);
                                // CatalogAdapter catalogAdapter = new CatalogAdapter(catalogList);
                                catalogRecycler.setHasFixedSize(true);
                                catalogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                                //catalogRecycler.setAdapter(catalogAdapter);
                                catalogprogress.setVisibility(View.GONE);
                            } catch (Exception e) {
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (Exception e) {
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
