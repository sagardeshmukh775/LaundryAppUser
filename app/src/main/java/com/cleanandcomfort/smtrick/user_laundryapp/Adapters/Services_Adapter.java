package com.cleanandcomfort.smtrick.user_laundryapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleanandcomfort.smtrick.user_laundryapp.R;

import java.util.ArrayList;
import java.util.List;

public class Services_Adapter extends RecyclerView.Adapter<Services_Adapter.ViewHolder> {

    private Context context;
    private List<String> list;


    public Services_Adapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public Services_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_list, parent, false);
        Services_Adapter.ViewHolder viewHolder = new Services_Adapter.ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final Services_Adapter.ViewHolder holder, final int position) {
        try {
            String serviceName = list.get(position);

            holder.service.setText(serviceName);
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView service;

        public ViewHolder(View itemView) {
            super(itemView);

            service = (TextView) itemView.findViewById(R.id.servicename);

        }
    }

    public void reload(ArrayList<String> leedsModelArrayList) {
        list.clear();
        list.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }
}
