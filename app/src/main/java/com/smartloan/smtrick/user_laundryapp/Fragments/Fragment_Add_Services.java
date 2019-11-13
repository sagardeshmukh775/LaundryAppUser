package com.smartloan.smtrick.user_laundryapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smartloan.smtrick.user_laundryapp.Repository.Impl.LeedRepositoryImpl;
import com.smartloan.smtrick.user_laundryapp.Models.MainCategory;
import com.smartloan.smtrick.user_laundryapp.Interface.OnFragmentInteractionListener;
import com.smartloan.smtrick.user_laundryapp.Dialogue.ProgressDialogClass;
import com.smartloan.smtrick.user_laundryapp.R;
import com.smartloan.smtrick.user_laundryapp.Repository.LeedRepository;
import com.smartloan.smtrick.user_laundryapp.Models.SubCategory;
import com.smartloan.smtrick.user_laundryapp.Adapters.ViewServicesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_Add_Services extends Fragment implements AdapterView.OnItemSelectedListener {
    private OnFragmentInteractionListener mListener;

    FirebaseStorage storage;
    StorageReference storageReference;
    private DatabaseReference mDatabaseRefMain;
    private DatabaseReference mDatabaseRefSub;

    private ArrayList<MainCategory> mainproductlist;
    private List<String> subproductlist;
    private List<SubCategory> subcategorylist;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> subcategorylist1 = new ArrayList<String>();


    LeedRepository leedRepository;
    ProgressDialogClass progressDialogClass;
    ViewServicesAdapter serviceAdapter;

    RecyclerView CommissionRecycle;
    ImageView AddCommission;

    int j = 0;

    public Fragment_Add_Services() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_services, container, false);
        // NOTE : We are calling the onFragmentInteraction() declared in the MainActivity
        // ie we are sending "Fragment 1" as title parameter when fragment1 is activated
        if (mListener != null) {
            mListener.onFragmentInteraction("Add Services");
        }

        mDatabaseRefSub = FirebaseDatabase.getInstance().getReference("SubCategory");
        mDatabaseRefMain = FirebaseDatabase.getInstance().getReference("MainCategory");

        mainproductlist = new ArrayList<>();
        subproductlist = new ArrayList<>();
        subcategorylist = new ArrayList<>();

        progressDialogClass = new ProgressDialogClass(getActivity());
        leedRepository = new LeedRepositoryImpl();

        CommissionRecycle = (RecyclerView) view.findViewById(R.id.recycler_view_commission);
        AddCommission = (ImageView) view.findViewById(R.id.addcommission);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        PrepareData();

        return view;
    }

    private void PrepareData() {
        mDatabaseRefMain.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mainproductlist.clear();
                for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                    MainCategory mainProducts = mainproductSnapshot.getValue(MainCategory.class);

//                    getSubCategory(mainProducts.getMaincategory());
                    mainproductlist.add(mainProducts);

                }
                serviceAdapter = new ViewServicesAdapter(mainproductlist);
                CommissionRecycle.setHasFixedSize(true);
                CommissionRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
                CommissionRecycle.setAdapter(serviceAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // NOTE: This is the part that usually gives you the error
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}