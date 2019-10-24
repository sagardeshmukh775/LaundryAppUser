package com.smartloan.smtrick.user_laundryapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class
Fragment_View_Service_Providers extends Fragment {

    private RecyclerView ServiceRecycler;
    private DatabaseReference mdataRefpatient;
    private ArrayList<MemberVO> catalogList;
    private ProgressDialog progressDialog;
    private Service_Providers_Adapter adapter;
    private EditText edtSearch;
    DatabaseReference databaseReference;
    String Language;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser Fuser;
    private String uid;
    private AppSharedPreference appSharedPreference;
    private ArrayList<User> service_providers;

    // int[] animationList = {R.anim.layout_animation_up_to_down};
    int i = 0;

    String number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_service_providers, container, false);

        // getActivity().getActionBar().setTitle("Products");
        appSharedPreference = new AppSharedPreference(getContext());
        progressDialog = new ProgressDialog(getContext());

        ServiceRecycler = (RecyclerView) view.findViewById(R.id.catalog_recycle);
        edtSearch = (EditText) view.findViewById(R.id.search_edit_text);

        catalogList = new ArrayList<>();
        service_providers = new ArrayList<>();

        getServiceProviders();

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
                    ServiceRecycler.removeAllViews();
                }

            }
        });


        return view;
    }

    private void setAdapter(String toString) {
        FirebaseDatabase.getInstance().getReference("ServiceProviders").orderByChild("role").equalTo("SERVICE PROVIDER").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                service_providers.clear();
                ServiceRecycler.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User service_provider = snapshot.getValue(User.class);
                    if (service_provider.getPincode() != null) {
                        if (service_provider.getPincode().toLowerCase().contains(toString)) {
                            service_providers.add(service_provider);
                        }
                    }
                }
                serAdapter(service_providers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getServiceProviders() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("role").equalTo("SERVICE PROVIDER");

        query.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            service_providers.clear();
            progressDialog.dismiss();
            //iterating through all the values in database
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                User service_provider = postSnapshot.getValue(User.class);

                service_providers.add(service_provider);
            }

            serAdapter(service_providers);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();

        }
    };


//    private void setAdapter(final String toString) {
//
//        databaseReference.child("Members").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                catalogList.clear();
//                ServiceRecycler.removeAllViews();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String uid = snapshot.getKey();
//                    MemberVO leedsModel = snapshot.getValue(MemberVO.class);
//
//                    if (leedsModel.getMemberbirthdate() != null && leedsModel.getMembercast() != null
//                            && leedsModel.getMembereducation() != null
//                            && leedsModel.getMemberage() != null) {
//                        if (leedsModel.getMemberbirthdate().toLowerCase().contains(toString)) {
//
//                            catalogList.add(leedsModel);
//                        } else if (leedsModel.getMembercast().toLowerCase().contains(toString)) {
//                            catalogList.add(leedsModel);
//
//                        } else if (leedsModel.getMembereducation().toLowerCase().contains(toString)) {
//                            catalogList.add(leedsModel);
//
//                        } else if (leedsModel.getMemberage().toLowerCase().contains(toString)) {
//                            catalogList.add(leedsModel);
//
//                        }
//                    }
//
//                }
//
//                serAdapter(catalogList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }


    private void serAdapter(ArrayList<User> leedsModels) {
        if (leedsModels != null) {
            if (adapter == null) {
                adapter = new Service_Providers_Adapter(getActivity(), leedsModels);
                ServiceRecycler.setAdapter(adapter);
                ServiceRecycler.setHasFixedSize(true);
                ServiceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                //   onClickListner();
            } else {
                ArrayList<User> leedsModelArrayList = new ArrayList<>();
                leedsModelArrayList.addAll(leedsModels);
                adapter.reload(leedsModelArrayList);
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}