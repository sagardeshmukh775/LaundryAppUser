package com.smartloan.smtrick.laundryapp;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ViewServicesAdapter extends RecyclerView.Adapter<ViewServicesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MainCategory> sublist;
    private ArrayList<SubCategory> subcategorylist;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabase;
    LeedRepository leedRepository;
    String key;
    SubCategoryAdapter adapter;

    public ViewServicesAdapter(ArrayList<MainCategory> catalogList) {
        sublist = catalogList;
    }

    @Override
    public ViewServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogsublist, parent, false);
        ViewServicesAdapter.ViewHolder viewHolder = new ViewServicesAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewServicesAdapter.ViewHolder holder, final int position) {

        leedRepository = new LeedRepositoryImpl();
        subcategorylist = new ArrayList<>();
        final MainCategory subcatname = sublist.get(position);
        holder.catalogSubname.setText(subcatname.getMaincategory());
        String serviceName = subcatname.getMaincategory();

        holder.subcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog1 = new Dialog(holder.subcardView.getContext());
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialogboxanimation);
                dialog1.setContentView(R.layout.customdialogboxsubcategory);

                final RecyclerView checklist = (RecyclerView) dialog1.findViewById(R.id.checklist_recycle);
                final Button btnsubmitchecklist = (Button) dialog1.findViewById(R.id.buttonaddchecklist);

                final ArrayList<String> checked = new ArrayList<>();

                leedRepository.readServicesByName(serviceName, new CallBack() {
                    @Override
                    public void onSuccess(Object object) {

                        if (object != null) {
                            subcategorylist = (ArrayList<SubCategory>) object;
                            for (SubCategory checked1 : subcategorylist) {
                                checked.add(checked1.getSubcatitem());
                            }

                            adapter = new SubCategoryAdapter(checked);
                            //adding adapter to recyclerview
                            checklist.setAdapter(adapter);
                            // CatalogAdapter catalogAdapter = new CatalogAdapter(catalogList);
                            checklist.setHasFixedSize(true);
                            checklist.setLayoutManager(new LinearLayoutManager(holder.subcardView.getContext()));

                        }
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
                dialog1.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return sublist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogSubname;
        public CardView subcardView;

        public ViewHolder(View itemView) {
            super(itemView);

            catalogSubname = (TextView) itemView.findViewById(R.id.subcatalog_name);
            subcardView = (CardView) itemView.findViewById(R.id.newcard);
        }
    }
}
