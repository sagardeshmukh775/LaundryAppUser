package com.smartloan.smtrick.laundryapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Request_Adapter extends RecyclerView.Adapter<Request_Adapter.ViewHolder> {

    private Context context;
    private List<UserServices> uploads;
    private List<String> servicesList;
    SubList_Adapter adapter;

    ArrayList<String> test;

    public Request_Adapter(Context context, List<UserServices> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_requests, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final UserServices upload = uploads.get(position);
        servicesList = new ArrayList<>();

        servicesList = upload.getSublist();
        holder.textViewName.setText(upload.getMaincat());
//        holder.textViewName.setTextColor(Color.parseColor("#3498DB"));
        adapter = new SubList_Adapter(holder.imagecard.getContext(), servicesList, (OnImageClickListener) holder.imagecard.getContext());

        //adding adapter to recyclerview
        holder.Recycle_Request.setAdapter(adapter);
        holder.Recycle_Request.setHasFixedSize(true);
        holder.Recycle_Request.setLayoutManager(new LinearLayoutManager(holder.imagecard.getContext()));


    }


    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public RecyclerView Recycle_Request;
        public CardView imagecard;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            Recycle_Request = (RecyclerView) itemView.findViewById(R.id.recyclerView_Requests);
            imagecard = (CardView) itemView.findViewById(R.id.cardimage);
        }
    }
}
