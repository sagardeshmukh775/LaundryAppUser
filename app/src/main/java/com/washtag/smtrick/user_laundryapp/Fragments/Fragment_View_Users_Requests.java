package com.washtag.smtrick.user_laundryapp.Fragments;

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
import com.washtag.smtrick.user_laundryapp.Adapters.Service_Providers_Requests_Approved_Adapter;
import com.washtag.smtrick.user_laundryapp.CallBack.CallBack;
import com.washtag.smtrick.user_laundryapp.Constants.Constant;
import com.washtag.smtrick.user_laundryapp.Models.MemberVO;
import com.washtag.smtrick.user_laundryapp.Models.Requests;
import com.washtag.smtrick.user_laundryapp.Preferences.AppSharedPreference;
import com.washtag.smtrick.user_laundryapp.R;
import com.washtag.smtrick.user_laundryapp.Repository.Impl.LeedRepositoryImpl;
import com.washtag.smtrick.user_laundryapp.Repository.LeedRepository;

import java.util.ArrayList;


public class Fragment_View_Users_Requests extends Fragment {

    private RecyclerView ServiceRecycler;
    private DatabaseReference mdataRefpatient;
    private ArrayList<MemberVO> catalogList;
    private ProgressDialog progressDialog;
    private Service_Providers_Requests_Approved_Adapter adapter;
    private EditText edtSearch;
    DatabaseReference databaseReference;
    String Language;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser Fuser;
    private String uid;
    private AppSharedPreference appSharedPreference;
    private ArrayList<Requests> service_providers;
    private ArrayList<Requests> service_providers1;
    private LeedRepository leedRepository;

    // int[] animationList = {R.anim.layout_animation_up_to_down};
    int i = 0;

    String number;
    private ArrayList<Requests> leedsArraylist1;
    private ArrayList<Requests> leedsArraylist;

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
        leedRepository = new LeedRepositoryImpl();

        ServiceRecycler = (RecyclerView) view.findViewById(R.id.catalog_recycle);
        edtSearch = (EditText) view.findViewById(R.id.search_edit_text);

        catalogList = new ArrayList<>();
        service_providers = new ArrayList<>();
        service_providers1 = new ArrayList<>();
        leedsArraylist = new ArrayList<>();
        leedsArraylist1 = new ArrayList<>();

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

    private void setAdapter(final String toString) {
        leedsArraylist1.clear();
        leedsArraylist.clear();
        leedRepository.readAllRequests(new CallBack() {
            @Override
            public void onSuccess(Object object) {

                if (object != null) {
                    leedsArraylist = (ArrayList<Requests>) object;

                    for (int i = 0; i < leedsArraylist.size(); i++) {
                        Requests leed = leedsArraylist.get(i);
                        try {
                            if (leed.getDate().toLowerCase().contains(toString)) {
                                leedsArraylist1.add(leed);
                            }
                        } catch (Exception e) {
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                serAdapter(leedsArraylist1);
            }

            @Override
            public void onError(Object object) {

            }
        });

    }


    private void getServiceProviders() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Query query = FirebaseDatabase.getInstance().getReference("Requests").orderByChild("userId").equalTo(appSharedPreference.getUserid());

        query.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            service_providers.clear();
            progressDialog.dismiss();
            //iterating through all the values in database
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Requests requests = postSnapshot.getValue(Requests.class);

                if (requests.getStatus() != null) {
                    if (requests.getStatus().equalsIgnoreCase(Constant.STATUS_GENERATED)) {
                        service_providers.add(requests);
                    }
                }
            }
            int size = service_providers.size() - 1;
            service_providers1.clear();
            for (int i = size; i >= 0; i--) {
                service_providers1.add(service_providers.get(i));
            }
            serAdapter(service_providers1);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();

        }
    };


    private void serAdapter(ArrayList<Requests> leedsModels) {
        if (leedsModels != null) {
            if (adapter == null) {
                adapter = new Service_Providers_Requests_Approved_Adapter(getActivity(), leedsModels);
                ServiceRecycler.setAdapter(adapter);
                ServiceRecycler.setHasFixedSize(true);
                ServiceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                //   onClickListner();
            } else {
                ArrayList<Requests> leedsModelArrayList = new ArrayList<>();
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