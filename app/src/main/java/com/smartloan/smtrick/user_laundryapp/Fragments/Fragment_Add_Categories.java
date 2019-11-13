package com.smartloan.smtrick.user_laundryapp.Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.user_laundryapp.Models.MainCategory;
import com.smartloan.smtrick.user_laundryapp.R;
import com.smartloan.smtrick.user_laundryapp.Models.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Add_Categories extends Fragment implements View.OnClickListener {


    private DatabaseReference mDatabaseRefMain;
    private DatabaseReference mDatabaseRefSub;

    ImageView imgMainCategory, imgSubCategory;
    EditText edtMaincategory, edtSubCategory;
    Spinner spinnerMainCategory;
    Button btnAddMainCat, btnAddSubCat;

    private List<String> mainproductlist;
    private List<String> subproductlist;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);


        mainproductlist = new ArrayList<>();
        subproductlist = new ArrayList<>();

        mDatabaseRefSub = FirebaseDatabase.getInstance().getReference("SubCategory");
        mDatabaseRefMain = FirebaseDatabase.getInstance().getReference("MainCategory");

        imgMainCategory = (ImageView) view.findViewById(R.id.dropmainproduct);
        imgSubCategory = (ImageView) view.findViewById(R.id.dropsubproduct);
        edtMaincategory = (EditText) view.findViewById(R.id.Main_product);
        edtMaincategory.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        edtSubCategory = (EditText) view.findViewById(R.id.Sub_product);
        spinnerMainCategory = (Spinner) view.findViewById(R.id.mainspinner);
        btnAddMainCat = (Button) view.findViewById(R.id.Add_Main_product);
        btnAddSubCat = (Button) view.findViewById(R.id.Add_sub_product);

        btnAddMainCat.setOnClickListener(this);
        btnAddSubCat.setOnClickListener(this);
        imgMainCategory.setOnClickListener(this);
        imgSubCategory.setOnClickListener(this);

        catelogspinnervalue();

        return view;


    }

    private void catelogspinnervalue() {
        try {

            mDatabaseRefMain.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mainproductlist.clear();
                    for (DataSnapshot mainproductSnapshot : dataSnapshot.getChildren()) {

                        MainCategory mainProducts = mainproductSnapshot.getValue(MainCategory.class);

                        mainproductlist.add(mainProducts.getMaincategory());

                    }
                    try {
                        ArrayAdapter<String> mainproadapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, mainproductlist);
                        mainproadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMainCategory.setAdapter(mainproadapter);


                    } catch (Exception e) {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

        }

    }


    @Override
    public void onClick(View v) {

        try {
            if (v == btnAddMainCat) {
                String mainitem = edtMaincategory.getText().toString();

                if (TextUtils.isEmpty(mainitem)) {
                    Toast.makeText(getContext(), "Enter Main Type!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean productPresent = false;

                try {

                    for (int i = 0; i <= mainproductlist.size(); i++) {
                        String mainpro = (mainproductlist.get(i));


                        if (mainitem.equals(mainpro)) {
                            productPresent = true;
                            break;
                        }

                    }
                } catch (Exception e) {
                }


                if (!productPresent) {

                    MainCategory mainProducts = new MainCategory(mainitem);
                    String uploadId = mDatabaseRefMain.push().getKey();
                    mDatabaseRefMain.child(uploadId).setValue(mainProducts);

                    Toast.makeText(getContext(), "Category Added", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "Category Name Allready Exist!", Toast.LENGTH_SHORT).show();
                }


            } else if (v == btnAddSubCat) {

                String maintype = spinnerMainCategory.getSelectedItem().toString();
                String subtype = edtSubCategory.getText().toString();

                if (TextUtils.isEmpty(subtype)) {
                    Toast.makeText(getContext(), "Enter Sub Category!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinnerMainCategory.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Select Main Category", Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean productPresent = false;

                try {

                    for (int i = 0; i <= subproductlist.size(); i++) {
                        String subpro = (subproductlist.get(i));


                        if (subtype.equals(subpro)) {
                            productPresent = true;
                            break;
                        }

                    }
                } catch (Exception e) {
                }

                if (!productPresent) {

                    SubCategory products = new SubCategory(maintype, subtype);

                    String uploadId = mDatabaseRefSub.push().getKey();
                    mDatabaseRefSub.child(uploadId).setValue(products);

                    Toast.makeText(getContext(), "SubCategory Added", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "SubCategory Allready Exist!", Toast.LENGTH_SHORT).show();
                }


            } else if (v == imgMainCategory) {
                PopupWindow popUp = popupWindowsort(v, mainproductlist);
                popUp.showAsDropDown(v, 0, 0);

            } else if (v == imgSubCategory) {

                subproductlist.clear();
                String mainpro = spinnerMainCategory.getSelectedItem().toString();

                Query query = FirebaseDatabase.getInstance().getReference("SubCategory")
                        .orderByChild("maincatitem")
                        .equalTo(mainpro);
                query.addValueEventListener(valueEventListener);

                PopupWindow popUp = popupWindowsort(v, subproductlist);
                popUp.showAsDropDown(v, 0, 0);

            }

        } catch (Exception e) {
        }


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                subproductlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SubCategory subproducts2 = snapshot.getValue(SubCategory.class);
                    subproductlist.add(subproducts2.getSubcatitem());
                }
                // subCatalogAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    private PopupWindow popupWindowsort(View v, List catalog) {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, catalog);
        // the drop down list is a list view
        ListView listViewSort = new ListView(getContext());
        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // some other visual settings for popup window
        popupWindow.update();
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        return popupWindow;
    }

}
