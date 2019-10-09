package com.smartloan.smtrick.laundryapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_Add_Services extends Fragment implements AdapterView.OnItemSelectedListener {
    private OnFragmentInteractionListener mListener;

    FirebaseStorage storage;
    StorageReference storageReference;
    private DatabaseReference mDatabaseRefMain;
    private DatabaseReference mDatabaseRefSub;

    private List<String> mainproductlist;
    private List<String> subproductlist;
    private List<SubCategory> subcategorylist;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> subcategorylist1 = new ArrayList<String>();


    LeedRepository leedRepository;
    ProgressDialogClass progressDialogClass;
    ExpandableListAdapter listAdapter;

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

//        AddCommission.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final Dialog dialog1 = new Dialog(getContext());
//                dialog1.setContentView(R.layout.add_commission_fragement);
//
//                ExpandableListView list = (ExpandableListView) dialog1.findViewById(R.id.lvExp);
//                listDataHeader = new ArrayList<String>();
//                listDataChild = new HashMap<String, List<String>>();
//                j = 0;
//
//                PrepareData();
//
//                listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);
//                // setting list adapter
//                list.setAdapter(listAdapter);
//                dialog1.show();
//            }
//
//        });

        return view;
    }

//    private void PrepareData() {
//        mDatabaseRefMain.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mainproductlist.clear();
//                for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {
//
//                    MainCategory mainProducts = mainproductSnapshot.getValue(MainCategory.class);
//
//                    mainproductlist.add(mainProducts.getMaincategory());
//
//                }
//                for (int i = 0; i < mainproductlist.size(); i++) {
//                    listDataHeader.add(mainproductlist.get(i));
//
//                }
//                for (int i = 0; i < listDataHeader.size(); i++) {
//                    String value = listDataHeader.get(i);
//                    leedRepository.readServicesByName(value, new CallBack() {
//                        @Override
//                        public void onSuccess(Object object) {
//                            subcategorylist.clear();
//
//                            if (object != null) {
//                                subcategorylist1.clear();
//                                subcategorylist = (ArrayList<SubCategory>) object;
//
//                                for (int i = 0; i < subcategorylist.size(); i++) {
//
//                                    subcategorylist1.add(subcategorylist.get(i).getSubcatitem());
//
//                                }
//                                listDataChild.put(listDataHeader.get(j), subcategorylist1);
//
//                            }
//                            j++;
//                        }
//
//                        @Override
//                        public void onError(Object object) {
////                Utility.showMessage(getActivity(), getMessage(R.string.registration_fail));
//                        }
//                    });
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });
//    }


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