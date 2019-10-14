package com.smartloan.smtrick.laundryapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class ViewServicesAdapter extends RecyclerView.Adapter<ViewServicesAdapter.ViewHolder> {

    private Context context;
    private List<MainCategory> sublist;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabase;
    String key;

    public ViewServicesAdapter(List<MainCategory> catalogList) {
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
        final MainCategory subcatname = sublist.get(position);
        holder.catalogSubname.setText(subcatname.getMaincategory());

        holder.subcardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
